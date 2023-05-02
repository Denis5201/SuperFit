package com.example.superfit.presentation.auth.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.superfit.R
import com.example.superfit.presentation.auth.models.SignUpEvent
import com.example.superfit.presentation.auth.models.SignUpUiState

@Composable
fun RegistrationInput(
    state: SignUpUiState,
    getEvent: (SignUpEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.82f)
                .padding(start = 54.dp, end = 40.dp)
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.42f))

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                AuthInput(
                    input = state.userName,
                    valChange = { getEvent(SignUpEvent.InputUserName(it)) },
                    name = stringResource(R.string.user_name)
                )
                Spacer(modifier = Modifier.padding(16.dp))

                AuthInput(
                    input = state.email,
                    valChange = { getEvent(SignUpEvent.InputEmail(it)) },
                    name = stringResource(R.string.email),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.padding(16.dp))

                AuthInput(
                    input = state.code,
                    valChange = { getEvent(SignUpEvent.InputCode(it)) },
                    name = stringResource(R.string.code),
                    isPassword = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                )
                Spacer(modifier = Modifier.padding(16.dp))

                AuthInput(
                    input = state.repeatCode,
                    valChange = { getEvent(SignUpEvent.InputRepeatCode(it)) },
                    name = stringResource(R.string.repeat_code),
                    isPassword = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                )
                Spacer(modifier = Modifier.padding(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { getEvent(SignUpEvent.SignUp) },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.sign_up),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_right),
                        contentDescription = null,
                        modifier = Modifier.padding(top = 2.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .align(Alignment.BottomCenter)
                .clickable { getEvent(SignUpEvent.NavigateToSignIn) },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
                contentDescription = null,
                modifier = Modifier.padding(top = 2.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}