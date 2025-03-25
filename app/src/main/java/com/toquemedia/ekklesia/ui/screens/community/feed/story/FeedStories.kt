package com.toquemedia.ekklesia.ui.screens.community.feed.story

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FeedStories(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        FeedAddStory(
            Modifier
                .padding(end = 6.dp)
        )

        List(3) {
            FeedStory(
                modifier = Modifier
                    .padding(end = 12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FeedStoriesPrev() {
    FeedStories()
}