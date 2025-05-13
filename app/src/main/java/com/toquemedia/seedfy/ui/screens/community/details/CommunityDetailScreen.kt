package com.toquemedia.seedfy.ui.screens.community.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.model.CommunityWithMembers
import com.toquemedia.seedfy.model.UserType
import com.toquemedia.seedfy.ui.composables.EkklesiaImage
import com.toquemedia.seedfy.utils.mocks.CommunityMock

@Composable
fun CommunityDetailScreen(
    modifier: Modifier = Modifier,
    data: CommunityWithMembers,
    currentUser: UserType? = null
) {

    val community = data.community
    val members = data.allMembers

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = modifier.height(32.dp))

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
            text = "${stringResource(R.string.community)} - ${if (members.size > 1) members.size else ""} ${stringResource(R.string.members)}"
        )

        Spacer(modifier = modifier.height(20.dp))

        Text(
            text = community.communityDescription,
            style = TextStyle(textAlign = TextAlign.Left),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = modifier.height(32.dp))

        InviteButton()

        Spacer(modifier = modifier.height(10.dp))

        Members(
            members = members,
            currentUser = currentUser
        )

        Spacer(modifier = modifier.height(32.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CommunityDetailScreenPrev() {
    CommunityDetailScreen(
        data = CommunityMock.getAllCommunityWithMembers().first(),
    )
}