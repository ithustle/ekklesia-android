package com.toquemedia.ekklesia.ui.screens.community.feed.story

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.composables.EkklesiaImage
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun FeedAddStory(
    modifier: Modifier = Modifier,
    onAddStory: () -> Unit = {}
) {
    Box(
        modifier
            .size(64.dp)
            .clickable {
                onAddStory()
            },
        contentAlignment = Alignment.BottomEnd
    ) {
        EkklesiaImage(
            model = "photo".toUri(),
            contentDescription = stringResource(R.string.profileTitleScreen),
            modifier = Modifier
                .padding(end = 8.dp)
                .size(64.dp)
                .clip(CircleShape)
        )
        Box(
            Modifier
                .offset(x = (-5).dp, y = (-2).dp)
                .clip(CircleShape)
                .background(color = Color.White)
                .size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = stringResource(R.string.add_stories_description),
                tint = PrincipalColor
            )
        }
    }
}

@Preview
@Composable
private fun FeedAddStoriesPrev() {
    FeedAddStory()
}