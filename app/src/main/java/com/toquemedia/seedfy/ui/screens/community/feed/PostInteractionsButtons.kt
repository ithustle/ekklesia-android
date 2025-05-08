package com.toquemedia.seedfy.ui.screens.community.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.model.PostType
import com.toquemedia.seedfy.ui.theme.PrincipalColor
import com.toquemedia.seedfy.utils.mocks.PostsMock

@Composable
fun PostInteractionsButtons(
    modifier: Modifier = Modifier,
    liked: Boolean = false,
    likesCount: Long = 0L,
    commentsCount: Long = 0L,
    post: PostType,
    onLikePost: (PostType) -> Unit = {},
    onRemoveLikePost: (PostType) -> Unit = {},
    onNavigateToComments: (String) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (liked) {
                        onRemoveLikePost(post)
                    } else {
                        onLikePost(post)
                    }
                },
                modifier = Modifier
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = if (liked) Icons.Filled.Favorite else Icons.Rounded.FavoriteBorder,
                    contentDescription = stringResource(R.string.like_icon_description),
                    tint = PrincipalColor,
                )
            }

            Text(
                text = if (likesCount == 0L) "" else likesCount.toString(),
                fontSize = 17.sp,
                color = PrincipalColor,
                fontWeight = FontWeight.Medium
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    onNavigateToComments(post.verseId)
                },
                modifier = Modifier
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.QuestionAnswer,
                    contentDescription = stringResource(R.string.comment_icon_description),
                    tint = PrincipalColor,
                )
            }

            Text(
                text = if (commentsCount == 0L) "" else commentsCount.toString(),
                fontSize = 17.sp,
                color = PrincipalColor,
                fontWeight = FontWeight.Medium
            )
        }

        IconButton(onClick = {}) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun PostInteractionsButtonsPrev() {
    PostInteractionsButtons(post = PostsMock.getPosts().first())
}