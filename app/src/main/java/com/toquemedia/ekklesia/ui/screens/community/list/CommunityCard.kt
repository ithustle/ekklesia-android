package com.toquemedia.ekklesia.ui.screens.community.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.CommunityWithMembers
import com.toquemedia.ekklesia.ui.composables.EkklesiaImage
import com.toquemedia.ekklesia.ui.theme.PrincipalColor
import com.toquemedia.ekklesia.utils.mocks.CommunityMock

@Composable
fun CommunityCard(
    modifier: Modifier = Modifier,
    community: CommunityWithMembers,
    onNavigateToCommunity: () -> Unit = {},
    onOpenMoreMenu: () -> Unit = {},
    contentMenu: @Composable () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onNavigateToCommunity()
            }
    ) {
        EkklesiaImage(
            model = community.community.communityImage.toUri(),
            contentDescription = stringResource(R.string.community_title),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(54.dp)
                .clip(shape = RoundedCornerShape(4.dp)),
        )

        Column {
            Text(
                text = community.community.communityName,
                fontSize = 20.sp,
                color = PrincipalColor,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${stringResource(R.string.members)}: ${community.allMembers.size}",
                fontSize = 13.sp,
                color = Color.DarkGray,
            )
        }

        Spacer(Modifier.weight(1f))

        Box {
            IconButton(onClick = onOpenMoreMenu) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = stringResource(R.string.more_option),
                    tint = Color.DarkGray
                )
            }
            contentMenu()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommunityCardPrev() {
    CommunityCard(
        community = CommunityMock.getAllCommunityWithMembers().first()
    )
}