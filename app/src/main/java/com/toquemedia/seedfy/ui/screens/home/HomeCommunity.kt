package com.toquemedia.seedfy.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.model.CommunityMemberType
import com.toquemedia.seedfy.model.CommunityType
import com.toquemedia.seedfy.ui.composables.EkklesiaButton
import com.toquemedia.seedfy.ui.composables.EkklesiaImage
import com.toquemedia.seedfy.ui.theme.PrincipalColor
import com.toquemedia.seedfy.utils.mocks.CommunityMock

@Composable
fun HomeCommunity(
    modifier: Modifier = Modifier,
    community: CommunityType,
    joiningToCommunity: Boolean = false,
    members: List<CommunityMemberType>,
    onJoinToCommunity: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(color = Color.White)
            .padding(vertical = 20.dp, horizontal = 12.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = PrincipalColor,
                        shape = RoundedCornerShape(6.dp),
                    )
            ) {
                EkklesiaImage(
                    model = community.communityImage.toUri(),
                    contentDescription = stringResource(R.string.community_description),
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(6.dp))
                )
            }

            Column {
                Text(
                    text = community.communityName,
                    fontSize = 20.sp,
                    color = PrincipalColor,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = community.communityDescription,
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "${members.size} membros",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(Modifier.height(22.dp))

        EkklesiaButton(
            filled = true,
            label = stringResource(R.string.join_to_community),
            loading = joiningToCommunity,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            onJoinToCommunity(community.id)
        }
    }
}

@Preview
@Composable
private fun HomeCommunityPrev() {
    HomeCommunity(
        community = CommunityMock.getAllCommunityWithMembers().first().community,
        members = CommunityMock.getAllCommunityWithMembers().first().allMembers
    )
}