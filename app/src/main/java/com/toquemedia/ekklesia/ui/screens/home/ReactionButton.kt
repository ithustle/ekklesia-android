package com.toquemedia.ekklesia.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.R

@Composable
fun ReactionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(R.string.like_icon_description),
            tint = Color.White,
        )
        Text(
            text = text,
            color = Color.White,
            fontSize = 13.sp
        )
    }
}

@Preview
@Composable
private fun ReactionButtonPrev() {
    ReactionButton(
        icon = Icons.Rounded.FavoriteBorder,
        text = "120"
    )
}