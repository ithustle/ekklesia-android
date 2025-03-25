package com.toquemedia.ekklesia.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.R

@Composable
fun VerseOfDay(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(214.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_verse),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                alpha = 0.25f,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column(

                ) {
                    Text(
                        text = "Versículo do dia",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 10.sp
                    )

                    Text(
                        text = "Mateus 5:10",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                Text(
                    text = "Felizes os perseguidos por causa da justiça, pois o reino dos céus lhes pertence.",
                    color = Color.White,
                    fontSize = 18.sp
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    ReactionButton(
                        icon = Icons.Rounded.FavoriteBorder,
                        text = "157",
                    )

                    ReactionButton(
                        icon = Icons.Outlined.ModeComment,
                        text = "57",
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun VerseOfDayPrev() {
    VerseOfDay()
}