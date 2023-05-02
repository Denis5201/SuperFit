package com.example.superfit.presentation.main

import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.superfit.R

@Composable
fun MainScreen() {
    Text(text = stringResource(R.string.app_name), style = MaterialTheme.typography.titleLarge)
}