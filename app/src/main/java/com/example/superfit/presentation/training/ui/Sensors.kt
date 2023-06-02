package com.example.superfit.presentation.training.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.superfit.presentation.training.models.TrainingEvent
import kotlin.math.absoluteValue

@Composable
fun LinearSensor(
    coordinate: Int,
    getEvent: (TrainingEvent) -> Unit
) {
    val context = LocalContext.current

    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

    var previous = 0.0f
    var isDown = false

    val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.values?.get(coordinate)?.let {
                if (it.absoluteValue > 1) {
                    if (it < 0 && previous > 0 && isDown) {
                        getEvent(TrainingEvent.DecreaseCount(1))
                        isDown = false
                        previous = 0.0f
                    } else if (it > 0 && previous < 0 && !isDown) {
                        isDown = true
                        previous = it
                    } else {
                        previous = it
                    }
                }
            }
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) { }
    }

    DisposableEffect(key1 = Unit) {
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        onDispose { sensorManager.unregisterListener(listener) }
    }
}

@Composable
fun StepSensor(
    getEvent: (TrainingEvent) -> Unit
) {
    val context = LocalContext.current

    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    var permission by remember { mutableStateOf(false) }

    var prev = -1

    val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.values?.get(0)?.let {
                if (prev != -1) {
                    getEvent(TrainingEvent.DecreaseCount((it.toInt() - prev)))
                }
                prev = it.toInt()
            }
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) { }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            permission = true
        } else {
            getEvent(TrainingEvent.BackToMainScreen)
        }
    }

    permission =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACTIVITY_RECOGNITION)
        } else {
            true
        }
    if (permission) {
        DisposableEffect(key1 = Unit) {
            sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            onDispose { sensorManager.unregisterListener(listener) }
        }
    } else {
        SideEffect {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                permissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
            }
        }
    }
}