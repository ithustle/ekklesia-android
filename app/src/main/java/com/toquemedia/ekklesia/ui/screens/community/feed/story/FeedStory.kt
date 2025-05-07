package com.toquemedia.ekklesia.ui.screens.community.feed.story

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.composables.EkklesiaImage
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun FeedStory(
    modifier: Modifier = Modifier,
    userPhoto: String = "",
    onClickToShowStory: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .size(68.dp)
            .border(width = 3.dp, color = PrincipalColor, shape = CircleShape)
            .clickable {
                onClickToShowStory()
            },
    ) {
        EkklesiaImage(
            model = userPhoto.toUri(),
            contentDescription = stringResource(R.string.profileTitleScreen),
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
    }
}

@Preview
@Composable
private fun FeedStoryPrev() {
    FeedStory()
}