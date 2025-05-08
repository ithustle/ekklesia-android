package com.toquemedia.seedfy.ui.screens.community.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.google.firebase.firestore.auth.User
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.extension.getBookWithChapterAndVersicle
import com.toquemedia.seedfy.extension.timeAgo
import com.toquemedia.seedfy.model.CommentType
import com.toquemedia.seedfy.model.PostType
import com.toquemedia.seedfy.model.UserType
import com.toquemedia.seedfy.ui.composables.EkklesiaImage
import com.toquemedia.seedfy.ui.composables.VerseToAnnotation
import com.toquemedia.seedfy.ui.theme.PrincipalColor
import com.toquemedia.seedfy.utils.mocks.PostsMock

@Composable
fun FeedPost(
    modifier: Modifier = Modifier,
    showLastComment: Boolean = false,
    showLikes: Boolean = false,
    liked: Boolean = false,
    hasStory: Boolean = false,
    post: PostType,
    comments: List<CommentType>,
    onNavigateToComments: (String) -> Unit = {},
    onLikePost: (PostType) -> Unit = {},
    onRemoveLikePost: (PostType) -> Unit = {},
    onNavigateToStory: (UserType) -> Unit = {},
    commentsCount: Long = 0L,
    likesCount: Long = 0L
) {

    val verseData = post.getBookWithChapterAndVersicle()

    Column(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        PostOwner(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            user = post.user!!,
            timeAgo = post.createdAt.timeAgo(),
            hasStory = hasStory,
            onNavigateToStory = onNavigateToStory,
            color = Color.DarkGray,
            postType = if (post.note == null) stringResource(R.string.post_type_without_note) else stringResource(
                R.string.post_type_with_note,
            )
        )

        Spacer(Modifier.height(14.dp))

        VerseToAnnotation(
            bookName = verseData.first,
            versicle = verseData.third,
            chapter = verseData.second,
            verse = post.verse.toString(),
            bookNameAsTitle = false,
            color = PrincipalColor,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clickable { onNavigateToComments(post.verseId) }
        )

        Spacer(Modifier.height(10.dp))

        post.note?.let {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(size = 4.dp))
                    .background(color = Color(0xFFE1E0E0))
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = it.note,
                    color = PrincipalColor,
                    fontSize = 13.sp
                )
            }
        }

        PostInteractionsButtons(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            liked = liked,
            commentsCount = commentsCount,
            likesCount = likesCount,
            onLikePost = onLikePost,
            onRemoveLikePost = onRemoveLikePost,
            post = post,
            onNavigateToComments = onNavigateToComments
        )

        if (showLikes) {
            Spacer(Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                List(post.firstUsersLiked.size) {
                    EkklesiaImage(
                        model = post.firstUsersLiked[it].photo?.toUri(),
                        contentDescription = stringResource(R.string.profileTitleScreen),
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .size(20.dp)
                            .clip(CircleShape)
                    )
                }

                if (post.likes > 4) {
                    Text(
                        text = "e mais ${post.likes - post.firstUsersLiked.size} ${stringResource(R.string.liked)}",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        if (showLastComment) {
            Spacer(Modifier.height(20.dp))
            FeedPostComment(commentary = comments.maxByOrNull { it.createdAt })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FeedPostPrev() {
    FeedPost(
        liked = true,
        post = PostsMock.getPosts().first(),
        comments = PostsMock.getPosts().first().comments,
    )
}