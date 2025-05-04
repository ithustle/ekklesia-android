package com.toquemedia.ekklesia.ui.screens.community.feed.story

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.toquemedia.ekklesia.model.StoryType
import com.toquemedia.ekklesia.model.UserType

@Composable
fun FeedStories(
    modifier: Modifier = Modifier,
    user: UserType? = null,
    hasStory: Boolean = false,
    myStories: List<StoryType>,
    stories: List<StoryType>,
    onShowStory: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        FeedAddStory(
            Modifier
                .padding(end = 12.dp),
            userPhoto = user?.photo.toString().toUri(),
            hasStory = hasStory,
            onShowStory = {
                if (hasStory) {
                    onShowStory()
                }
            }
        )

        List(stories.size) {
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
    FeedStories(stories = emptyList(), myStories = emptyList())
}