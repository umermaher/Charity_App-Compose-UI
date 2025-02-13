package com.umermahar.charityapp.food.presentation.billing_details.components

import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly

@Composable
fun CardInputField(
    digits: String,
    focusRequester: FocusRequester,
    onFocusChange: (Boolean) -> Unit,
    onDigitsChange: (String) -> Unit,
    onKeyboardBack: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary
    ),
    shape: Shape = RectangleShape
) {
    val text by remember (digits) {
        mutableStateOf(
            TextFieldValue(
                text = digits,
                selection = TextRange(
                    index = digits.length
                )
            )
        )
    }

    var isFocused by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = shape
            )
            .border(
                width = 1.dp,
                color = if (isFocused) {
                    MaterialTheme.colorScheme.secondary
                } else Color.Transparent,
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = text,
            onValueChange = { newText ->
                val newDigits = newText.text
                if(newDigits.length <= 4 && newDigits.isDigitsOnly()) {
                    onDigitsChange(newDigits)
                }
            },
            singleLine = true,
            textStyle = textStyle,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            visualTransformation = PasswordVisualTransformation('*'), // Masks input as '*'
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusEvent {
                    isFocused = it.isFocused
                    onFocusChange(it.isFocused)
                }
                .onKeyEvent {
                    val didPressDelete = it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL
                    if(didPressDelete && digits.isEmpty()) {
                        onKeyboardBack()
                    }
                    false
                },
            decorationBox = { innerBox ->
                innerBox()
                if(!isFocused && digits.isEmpty()) {
                    Text(
                        text = "-",
                        style = textStyle,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                }
            }
        )
    }
}