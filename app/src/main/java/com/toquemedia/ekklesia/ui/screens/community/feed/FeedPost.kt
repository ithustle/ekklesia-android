package com.toquemedia.ekklesia.ui.screens.community.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
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
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.composables.EkklesiaImage
import com.toquemedia.ekklesia.ui.composables.VerseToAnnotation
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun FeedPost(
    modifier: Modifier = Modifier,
    hasNote: Boolean = false,
    showComments: Boolean = false,
    showLikes: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        PostOwner()

        Spacer(Modifier.height(14.dp))

        VerseToAnnotation(
            bookName = "Provérbios",
            versicle = 1,
            chapter = "12",
            verse = "Os tesouros de origem desonesta não servem para nada, mas a retidão livra da morte.",
            bookNameAsTitle = false
        )

        Spacer(Modifier.height(10.dp))

        if (hasNote) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(size = 4.dp))
                    .background(color = Color(0xFFE1E0E0))
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Glória à Deus, toda a hora e todo o louvor! Em nome de Jesus",
                    color = PrincipalColor,
                    fontSize = 13.sp
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.FavoriteBorder,
                contentDescription = stringResource(R.string.like_icon_description),
                tint = PrincipalColor,
                modifier = Modifier.size(20.dp)
            )

            Icon(
                imageVector = Icons.Outlined.ModeComment,
                contentDescription = stringResource(R.string.like_icon_description),
                tint = PrincipalColor,
                modifier = Modifier.size(20.dp)
            )
        }

        if (showLikes) {
            Spacer(Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                List(4) {
                    EkklesiaImage(
                        model = "photo".toUri(),
                        contentDescription = stringResource(R.string.profileTitleScreen),
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .size(24.dp)
                            .clip(CircleShape)
                    )
                }

                Text(
                    text = "e mais 4 gostaram",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }

        if (showComments) {
            Spacer(Modifier.height(20.dp))
            FeedPostComment()
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun FeedPostPrev() {
    FeedPost(
        hasNote = true,
        showComments = true,
        showLikes = true,
        modifier = Modifier.padding(16.dp)
    )
}