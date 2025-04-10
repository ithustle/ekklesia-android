package com.toquemedia.ekklesia.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.VerseType
import com.toquemedia.ekklesia.services.StatsVerseOfDay
import com.toquemedia.ekklesia.ui.theme.cosmicParadiseGradient

@Composable
fun VerseOfDay(
    modifier: Modifier = Modifier,
    verse: VerseType?,
    stats: StatsVerseOfDay,
    onLikeVerseOfDay: (Boolean) -> Unit = {},
    onShareVerseOfDay: () -> Unit = {},
    isUserLiked: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.Black)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .background(brush = cosmicParadiseGradient)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column(
                    modifier = modifier
                        .padding(bottom = 80.dp)
                ) {
                    Text(
                        text = stringResource(R.string.verse_of_the_day),
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 10.sp,
                        lineHeight = 2.sp
                    )

                    Text(
                        text = "${verse?.bookName} ${verse?.chapter}:${verse?.versicle}",
                        color = Color.White,
                        fontSize = 14.sp,
                        lineHeight = 2.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                Text(
                    text = verse?.text.toString(),
                    color = Color.White,
                    fontSize = 18.sp
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .padding(top = 80.dp)
                        .fillMaxWidth()
                ) {
                    ReactionButton(
                        iconVector = if (isUserLiked) Icons.Filled.Favorite else Icons.Rounded.FavoriteBorder,
                        text = stats.likes.toString(),
                        onClick = {
                            onLikeVerseOfDay(isUserLiked)
                        }
                    )

                    ReactionButton(
                        iconVector = Icons.Outlined.Share,
                        text = stats.shares.toString(),
                        onClick = onShareVerseOfDay
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun VerseOfDayPrev() {
    VerseOfDay(
        verse = VerseType(
            bookName = "Mateus",
            chapter = 5,
            versicle = 10,
            text = "Felizes os perseguidos por causa da justiça, pois o reino dos céus lhes pertence."
        ),
        stats = StatsVerseOfDay(),
        isUserLiked = true
    )
}