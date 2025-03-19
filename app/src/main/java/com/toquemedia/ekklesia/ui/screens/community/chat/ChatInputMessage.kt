package com.toquemedia.ekklesia.ui.screens.community.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun ChatInputMessage(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color = Color.White)
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {

        BasicTextField(
            value = "",
            onValueChange = {
                //onChangeValue(TextFieldValue(it, TextRange(it.length)))
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 15.sp
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
            ),
            modifier = modifier
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                .weight(1f)
                .height(height = 46.dp)
                .padding(vertical = 12.dp, horizontal = 10.dp),
            decorationBox = { innerTextField ->
                Text(
                    stringResource(R.string.message_placeholder),
                    color = Color.Gray,
                    fontSize = 15.sp
                )
                innerTextField()
            }
        )
        Icon(
            imageVector = Icons.Rounded.Mic,
            contentDescription = stringResource(R.string.message_voice_description),
            tint = PrincipalColor
        )
    }
}

@Preview
@Composable
private fun ChatInputMessagePrev() {
    ChatInputMessage()
}