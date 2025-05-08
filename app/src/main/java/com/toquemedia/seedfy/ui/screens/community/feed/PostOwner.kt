package com.toquemedia.seedfy.ui.screens.community.feed

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.extension.timeAgo
import com.toquemedia.seedfy.model.UserType
import com.toquemedia.seedfy.ui.composables.EkklesiaImage
import com.toquemedia.seedfy.ui.theme.PrincipalColor
import com.toquemedia.seedfy.utils.mocks.PostsMock

@Composable
fun PostOwner(
    user: UserType,
    postType: String? = null,
    hasStory: Boolean = false,
    timeAgo: String,
    color: Color,
    modifier: Modifier = Modifier,
    onNavigateToStory: (UserType) -> Unit = {}
) {

    val modifierStory = if (hasStory) Modifier
        .size(42.dp)
        .clip(CircleShape)
        .border(width = 3.dp, color = PrincipalColor, shape = CircleShape) else
        Modifier
            .size(42.dp)
            .clip(CircleShape)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable {
                if (hasStory) { onNavigateToStory(user) }
            }
    ) {
        EkklesiaImage(
            model = user.photo?.toUri(),
            contentDescription = stringResource(R.string.profileTitleScreen),
            modifier = modifierStory /*Modifier
                    .padding(end = 12.dp)
                    .size(42.dp)
                    .clip(CircleShape) */
        )

        Spacer(Modifier.width(8.dp))

        Column {
            Text(
                text = user.displayName.toString(),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 2.sp,
                color = color
            )

            postType?.let {
                Text(
                    text = it,
                    fontSize = 10.sp,
                    color = color,
                    lineHeight = 2.sp
                )
            }
            Text(
                text = timeAgo,
                fontSize = 10.sp,
                color = color,
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
        color = Color.DarkGray,
        postType = stringResource(R.string.post_type_without_note),
        timeAgo = PostsMock.getPosts().first().createdAt.timeAgo(),
        modifier = Modifier
            .fillMaxWidth()
    )
}