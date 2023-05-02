package com.example.superfit.presentation.auth.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthInput(
    input: String?,
    valChange: (String) -> Unit,
    name: String,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    val interactionSource = remember { MutableInteractionSource() }
    val visualTransformation = if (isPassword) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }

    BasicTextField(
        value = input ?: "",
        onValueChange = { valChange(it) },
        modifier = Modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        visualTransformation = visualTransformation,
        decorationBox = {
            TextFieldDefaults.TextFieldDecorationBox(
                value = input ?: "",
                innerTextField = it,
                enabled = true,
                singleLine = true,
                placeholder = {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                },
                colors = textFieldColors(
                    textColor = Color.White,
                    focusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                    disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                    containerColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                interactionSource = interactionSource,
                contentPadding = PaddingValues(horizontal = 2.dp, vertical = 3.dp),
                visualTransformation = visualTransformation
            )
        }
    )
}

@Composable
fun PasswordButton(
    digit: String,
    click: (String) -> Unit,
    modifier: Modifier
) {
    OutlinedButton(
        onClick = { click(digit) },
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimary),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
    ) {
        Text(
            text = digit,
            modifier = Modifier.align(Alignment.CenterVertically),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.W300
        )
    }
}