package com.example.superfit.presentation.auth.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.superfit.R
import com.example.superfit.presentation.auth.models.SignInEvent

@Composable
fun PasswordInput(
    userName: String,
    getEvent: (SignInEvent) -> Unit
) {
    BackHandler {
        getEvent(SignInEvent.BackToInputLogin)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp, top = 20.dp)
                .clickable { getEvent(SignInEvent.BackToInputLogin) },
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.88f)
                .padding(start = 40.dp, end = 40.dp)
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.4f))

            Text(
                text = userName,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.padding(22.dp))

            var biasList by remember { mutableStateOf(getAlignmentValues()) }
            val listAlignment = animateAlignmentAsState(biasList)

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                for (i in 1..9) {
                    PasswordButton(
                        digit = "$i",
                        click = {
                            getEvent(SignInEvent.InputPassword(i.toString()))
                            biasList = getAlignmentValues()
                        },
                        modifier = Modifier
                            .fillMaxSize(0.28f)
                            .aspectRatio(1f)
                            .align(listAlignment[i - 1].value)
                    )
                }
            }
        }
    }
}

@Composable
fun animateAlignmentAsState(
    targetBiasListValue: List<Pair<Float, Float>>
): List<State<BiasAlignment>> {
    val list = mutableListOf<State<BiasAlignment>>()
    for (i in 0..8) {
        val horizontal by animateFloatAsState(targetBiasListValue[i].first)
        val vertical by animateFloatAsState(targetBiasListValue[i].second)
        list.add( remember { derivedStateOf { BiasAlignment(horizontal, vertical) } })
    }
    return list
}

fun getAlignmentValues(): List<Pair<Float, Float>> {
    val horizontal = listOf(-1f, 0f, 1f).shuffled()
    val vertical = listOf(-1f, 0f, 1f).shuffled()
    val list = mutableListOf<Pair<Float, Float>>()

    horizontal.forEach { h ->
        vertical.forEach { v ->
            list.add(Pair(h, v))
        }
    }
    return list
}