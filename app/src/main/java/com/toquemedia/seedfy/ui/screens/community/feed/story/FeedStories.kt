package com.toquemedia.seedfy.ui.screens.community.feed.story

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.toquemedia.seedfy.model.StoryType
import com.toquemedia.seedfy.model.UserType
import com.toquemedia.seedfy.utils.mocks.StoriesMock

@Composable
fun FeedStories(
    modifier: Modifier = Modifier,
    user: UserType? = null,
    hasStory: Boolean = false,
    stories: List<StoryType>,
    onShowStory: (UserType) -> Unit = {}
) {

    val uniqueStories = stories.distinctBy { it.user?.id }

    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        FeedAddStory(
            Modifier.padding(end = 12.dp),
            userPhoto = user?.photo.toString().toUri(),
            hasStory = hasStory,
            onShowStory = {
                if (hasStory && user != null) {
                    onShowStory(user)
                }
            }
        )

        List(uniqueStories.size) {
            val story = uniqueStories[it]
            FeedStory(
                userPhoto = story.user?.photo.toString(),
                modifier = Modifier
                    .padding(end = 12.dp),
                onClickToShowStory = {
                    story.user?.let { onShowStory(it) }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FeedStoriesPrev() {
    FeedStories(stories = StoriesMock.getAll())
}