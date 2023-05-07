package com.example.superfit.presentation.body.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.superfit.R
import com.example.superfit.presentation.body.models.BodyEvent
import com.example.superfit.presentation.body.models.BodyUiState

@Composable
fun ChangeParamsDialog(
    title: String,
    state: BodyUiState,
    getEvent: (BodyEvent) -> Unit
) {

    val text = state.currentChangingText

    Dialog(onDismissRequest = { getEvent(BodyEvent.CloseChangingParamsDialog) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(2.dp))
                .padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.change_value, title.lowercase()),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.padding(12.dp))

            TextField(
                value = text,
                onValueChange = if (state.changingWeight) {
                    { getEvent(BodyEvent.NewWeightValue(it)) }
                } else {
                    { getEvent(BodyEvent.NewHeightValue(it)) }
                },
                label = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.new_value),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colorScheme.secondaryContainer,
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colorScheme.onTertiary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onTertiary,
                    placeholderColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )

            Spacer(modifier = Modifier.padding(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    modifier = Modifier.clickable { getEvent(BodyEvent.CloseChangingParamsDialog) },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.padding(start = 24.dp))

                Text(
                    text =  stringResource(R.string.change),
                    modifier = Modifier.clickable {
                        if (state.changingWeight) {
                            getEvent(BodyEvent.NewWeightValue(text, true))
                        } else {
                            getEvent(BodyEvent.NewHeightValue(text, true))
                        }
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

}