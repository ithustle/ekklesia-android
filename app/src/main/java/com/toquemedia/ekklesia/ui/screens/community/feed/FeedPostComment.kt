package com.toquemedia.ekklesia.ui.screens.community.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
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
import com.toquemedia.ekklesia.ui.composables.EkklesiaImage
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun FeedPostComment(modifier: Modifier = Modifier) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                EkklesiaImage(
                    model = "photo".toUri(),
                    contentDescription = stringResource(R.string.profileTitleScreen),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(24.dp)
                        .clip(CircleShape)
                )

                Text(
                    text = "Elsa Tomás",
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp,
                    color = PrincipalColor
                )
            }

            Text(
                text = "1min",
                fontSize = 10.sp,
                color = Color.DarkGray
            )
        }

        Column(
            Modifier.padding(start = 32.dp, end = 12.dp)
        ) {
            Text(
                text = "Glória à Deus, toda a honra e todo o louvor! Em nome de Jesus Cristo.",
                fontSize = 13.sp,
                maxLines = 3,
                color = PrincipalColor,
            )
            Spacer(Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.FavoriteBorder,
                    contentDescription = stringResource(R.string.like_icon_description),
                    tint = PrincipalColor,
                    modifier = Modifier
                        .size(16.dp)
                )

                Text(
                    text = "1k",
                    fontSize = 12.sp,
                    color = PrincipalColor
                )
            }
        }
    }
}

@Preview
@Composable
private fun FeedPostCommentPrev() {
    FeedPostComment()
}