package com.toquemedia.seedfy.ui.screens.community.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LiveTv
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.extension.formatTime
import com.toquemedia.seedfy.extension.timeAgo
import com.toquemedia.seedfy.extension.toColor
import com.toquemedia.seedfy.model.CommentType
import com.toquemedia.seedfy.model.PostType
import com.toquemedia.seedfy.model.UserType
import com.toquemedia.seedfy.ui.composables.VideoPresence
import com.toquemedia.seedfy.utils.mocks.WorshipMock

@Composable
fun FeedPostWorship(
    modifier: Modifier = Modifier,
    post: PostType,
    liked: Boolean = false,
    comments: List<CommentType> = emptyList(),
    onNavigateToWorship: (String) -> Unit = {},
    onNavigateToComments: (String) -> Unit = {},
    onLikePost: (PostType) -> Unit = {},
    onRemoveLikePost: (PostType) -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = modifier
                .clickable {
                    onNavigateToWorship(post.verseId)
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PostOwner(
                    modifier = Modifier,
                    user = post.user!!,
                    timeAgo = post.createdAt.timeAgo(),
                    color = Color.DarkGray,
                    postType = stringResource(R.string.post_type_worship)
                )

                Spacer(modifier = Modifier.width(12.dp))
            }

            Text(
                text = "${post.worship!!.bookName} ${post.worship.chapter}:${post.worship.versicle}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                fontWeight = FontWeight.Medium,
                color = post.worship.backgroundColor.toColor(),
                fontSize = 18.sp
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = post.worship.backgroundColor.toColor()
                )
            ) {
                Text(
                    text = "\"${post.worship.verse}\"",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }

            Text(
                text = post.worship.worship.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                fontSize = 15.sp,
                lineHeight = 24.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            post.worship.video?.let {
                VideoPresence(
                    icon = Icons.Rounded.LiveTv,
                    label = "Contém video devocional",
                    duration = post.worship.durationVideo?.formatTime() ?: "",
                    backgroundColor = post.worship.backgroundColor.toColor()
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                /*IconButton(
                    onClick = {
                        if (liked) {
                            onRemoveLikePost(post)
                        } else {
                            onLikePost(post)
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (liked) Icons.Filled.Favorite else Icons.Rounded.FavoriteBorder,
                        contentDescription = stringResource(R.string.like_icon_description),
                        tint = PrincipalColor
                    )
                }

                IconButton(
                    onClick = {
                        onNavigateToComments(post.verseId)
                    }
                ) {
                    Icon(
                        Icons.Filled.QuestionAnswer,
                        contentDescription = stringResource(R.string.comment_icon_description),
                        tint = PrincipalColor
                    )
                }

                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        Icons.Outlined.BookmarkBorder,
                        contentDescription = stringResource(R.string.bookmark_icon_description),
                        tint = PrincipalColor
                    )
                }*/
            }
        }

        if (comments.isNotEmpty()) {
            Spacer(Modifier.height(20.dp))
            FeedPostComment(commentary = comments.maxByOrNull { it.createdAt })
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun FeedPostWorshipPrev() {
    FeedPostWorship(
        post = PostType(
            worship = WorshipMock.getAll().first(),
            user = UserType(
                displayName = "Célio Garcia",
                photo = ""
            ),
        ),
    )
}