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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EkklesiaTextField(
    value: String,
    placeholder: String,
    height: Dp,
    singleLine: Boolean = false,
    enabled: Boolean = true,
    onChangeValue: (TextFieldValue) -> Unit = {}
) {
    Box {
        BasicTextField(
            value = value,
            onValueChange = {
                onChangeValue(TextFieldValue(it, TextRange(it.length)))
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 15.sp
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
            ),
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                .fillMaxWidth()
                .height(height = height)
                .padding(vertical = 12.dp, horizontal = 10.dp),
            singleLine = singleLine,
            enabled = enabled
        )

        if (value.isEmpty()) {
            Text(
                text = placeholder,
                fontStyle = FontStyle.Italic,
                color = Color.Gray,
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 10.dp)
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
        enabled = false
    )
}