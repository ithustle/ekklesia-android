package com.toquemedia.seedfy.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.seedfy.model.VerseType
import com.toquemedia.seedfy.services.StatsVerseOfDay

@Composable
fun VerseSection(
    verse: VerseType? = null,
    stats: StatsVerseOfDay = StatsVerseOfDay(),
    isUserLiked: Boolean = false,
    onLike: (Boolean) -> Unit = {},
    onShare: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(16.dp))

        if (verse == null) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(214.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(20.dp),
                    strokeWidth = 2.dp
                )
            }
        } else {
            VerseOfDay(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp)),
                verse = verse,
                stats = stats,
                isUserLiked = isUserLiked,
                onLikeVerseOfDay = onLike,
                onShareVerseOfDay = onShare
            )
        }
    }
}

@Preview
@Composable
private fun VerseSectionPrev() {
    VerseSection()
}