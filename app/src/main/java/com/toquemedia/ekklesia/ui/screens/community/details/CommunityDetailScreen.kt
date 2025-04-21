package com.toquemedia.ekklesia.ui.screens.community.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.CommunityMemberType
import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.ui.composables.EkklesiaImage
import com.toquemedia.ekklesia.ui.composables.EkklesiaTopBar
import com.toquemedia.ekklesia.utils.mocks.CommunityMock

@Composable
fun CommunityDetailScreen(
    modifier: Modifier = Modifier,
    community: CommunityType,
    members: List<CommunityMemberType> = emptyList()
) {
    Scaffold(
        topBar = {
            EkklesiaTopBar(
                title = "",
                isBackgroundTransparent = true,

                )
        },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .offset(y = (-56).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EkklesiaImage(
                model = community.communityImage.toUri(),
                contentDescription = stringResource(R.string.community_description),
                modifier = modifier
                    .size(124.dp)
                    .clip(CircleShape)
            )

            Text(
                text = community.communityName,
                fontSize = 24.sp,
                lineHeight = 34.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Comunidade - ${members.size} membros"
            )

            Spacer(modifier = modifier.height(20.dp))

            Text(
                text = community.communityDescription,
                style = TextStyle(textAlign = TextAlign.Left)
            )

            Spacer(modifier = modifier.height(20.dp))

            InviteButton()
        }
    }
}



@Preview(showSystemUi = true)
@Composable
private fun CommunityDetailScreenPrev() {
    CommunityDetailScreen(
        community = CommunityMock.getAll().first(),
        members = CommunityMock.getAllCommunityWithMembers().first().allMembers
    )
}