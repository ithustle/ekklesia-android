package com.toquemedia.seedfy.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.R

@Composable
fun ReactionButton(
    modifier: Modifier = Modifier,
    iconVector: ImageVector? = null,
    iconPainter: Painter? = null,
    text: String,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)

    ) {
        if (iconVector != null) {
            Icon(
                imageVector = iconVector,
                contentDescription = stringResource(R.string.like_icon_description),
                tint = Color.White,
            )
        } else if (iconPainter != null) {
            Icon(
                painter = iconPainter,
                contentDescription = stringResource(R.string.like_icon_description),
                tint = Color.White,
            )
        }

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
        iconVector = Icons.Rounded.FavoriteBorder,
        text = "120"
    )
}