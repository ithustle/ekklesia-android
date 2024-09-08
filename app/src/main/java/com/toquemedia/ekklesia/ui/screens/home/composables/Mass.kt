package com.toquemedia.ekklesia.ui.screens.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Mass(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .background(color = Color.DarkGray)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = "João",
                fontSize = 12.sp,
                color = Color.White
            )
            Text(
                "20:19-23",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "João",
                fontSize = 32.sp,
                color = Color.White
            )
            Text(
                "20:19-23",
                fontSize = 30.sp,
                color = Color.White
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Favorite,
                    contentDescription = "like",
                    tint = Color.White,
                )
                Text(
                    "55",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Message,
                    contentDescription = "Comments",
                    tint = Color.White
                )
                Text(
                    "55",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun MassPrev() {
    Mass(
        modifier = Modifier
            .height(210.dp)
    )
}