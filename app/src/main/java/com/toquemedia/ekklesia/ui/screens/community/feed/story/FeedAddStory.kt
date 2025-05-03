package com.toquemedia.ekklesia.ui.screens.community.feed.story

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
    userPhoto: Uri,
    hasStory: Boolean = false,
    onShowStory: () -> Unit = {}
) {

    val modifierStory = if (hasStory) Modifier
        .size(64.dp)
        .clip(CircleShape)
        .border(width = 3.dp, color = PrincipalColor, shape = CircleShape) else
        Modifier
            .size(64.dp)
            .clip(CircleShape)

    Box(
        modifier
            .size(64.dp)
            .clickable {
                onShowStory()
            },
        contentAlignment = Alignment.BottomEnd
    ) {
        EkklesiaImage(
            model = userPhoto,
            contentDescription = stringResource(R.string.profileTitleScreen),
            modifier = modifierStory,
        )
    }
}

@Preview
@Composable
private fun FeedAddStoriesPrev() {
    FeedAddStory(userPhoto = "".toUri())
}