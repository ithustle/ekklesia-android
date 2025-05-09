package com.toquemedia.seedfy.ui.screens.bible.worship

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.R

import com.toquemedia.seedfy.ui.theme.PrincipalColor

@Composable
fun AddResourceForWorship(
    text: String,
    color: Color,
    onAddVerseToWorship: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .clickable {
                onAddVerseToWorship()
            }
    ) {
        Icon(
            imageVector = Icons.Rounded.AddBox,
            contentDescription = null,
            tint = color
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = text,
            color = color,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
private fun AddResourceForWorshipPrev() {
    AddResourceForWorship(
        text = stringResource(R.string.tap_to_add_verse),
        color = PrincipalColor,
        onAddVerseToWorship = {}
    )
}