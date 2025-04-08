package com.toquemedia.ekklesia.ui.screens.community.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.extension.timeAgo
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.ui.composables.EkklesiaImage
import com.toquemedia.ekklesia.utils.mocks.PostsMock

@Composable
fun PostOwner(
    user: UserType,
    postType: String,
    timeAgo: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        EkklesiaImage(
            model = user.photo?.toUri(),
            contentDescription = stringResource(R.string.profileTitleScreen),
            modifier = Modifier
                .padding(end = 12.dp)
                .size(42.dp)
                .clip(CircleShape)
        )

        Column {
            Text(
                text = user.displayName.toString(),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 2.sp
            )

            Text(
                text = postType,
                fontSize = 10.sp,
                color = Color.DarkGray,
                lineHeight = 2.sp
            )
            Text(
                text = timeAgo,
                fontSize = 10.sp,
                color = Color.DarkGray,
                lineHeight = 2.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PostOwnerPrev() {
    PostOwner(
        user = UserType(
            id = "ok",
            displayName = "João Silva",
            photo = "".toUri().toString()
        ),
        postType = stringResource(R.string.post_type_without_note),
        timeAgo = PostsMock.getPosts().first().createdAt.timeAgo(),
        modifier = Modifier
            .fillMaxWidth()
    )
}