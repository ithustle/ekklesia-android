package com.toquemedia.ekklesia.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun EkklesiaTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    height: Dp,
    singleLine: Boolean = false,
    enabled: Boolean = true,
    imeAction: ImeAction,
    onChangeValue: (TextFieldValue) -> Unit = {}
) {
    var textFieldValueState by remember {
        mutableStateOf(TextFieldValue(value, TextRange(value.length)))
    }

    LaunchedEffect(value) {
        if (value != textFieldValueState.text) {
            textFieldValueState = TextFieldValue(value, TextRange(value.length))
        }
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = textFieldValueState,
            onValueChange = { newValue ->
                textFieldValueState = newValue

                val processedText = if (newValue.text.isNotEmpty() && value.isEmpty()) {
                    TextFieldValue(
                        newValue.text.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                        },
                        newValue.selection
                    )
                } else {
                    val textEndsWithPeriodSpace = value.endsWith(". ") &&
                            newValue.text.length > value.length

                    if (textEndsWithPeriodSpace) {
                        val lastPeriodPos = newValue.text.lastIndexOf(". ") + 2
                        if (lastPeriodPos < newValue.text.length && newValue.text[lastPeriodPos].isLowerCase()) {
                            val before = newValue.text.substring(0, lastPeriodPos)
                            val after = newValue.text.substring(lastPeriodPos)

                            TextFieldValue(
                                before + after.replaceFirstChar {
                                    it.titlecase(Locale.getDefault())
                                },
                                newValue.selection
                            )
                        } else {
                            newValue
                        }
                    } else {
                        newValue
                    }
                }

                textFieldValueState = processedText
                onChangeValue(processedText)
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 15.sp
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                imeAction = imeAction
            ),
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                .fillMaxWidth()
                .height(height = height)
                .padding(vertical = 12.dp, horizontal = 10.dp),
            singleLine = singleLine,
            enabled = enabled,
            cursorBrush = SolidColor(Color.Black)
        )

        if (value.isEmpty()) {
            Text(
                text = placeholder,
                color = Color.Gray,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 10.dp)
                    .align(Alignment.CenterStart)
            )
        }
    }
}

@Preview
@Composable
private fun EkklesiaTextFieldPrev() {
    EkklesiaTextField(
        value = "",
        placeholder = "Placeholder",
        height = 41.dp,
        singleLine = true,
        onChangeValue = {},
        enabled = false,
        imeAction = ImeAction.Next
    )
}