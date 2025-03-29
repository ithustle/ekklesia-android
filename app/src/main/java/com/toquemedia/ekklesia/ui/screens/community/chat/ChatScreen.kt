package com.toquemedia.ekklesia.ui.screens.community.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.toquemedia.ekklesia.extension.toCommunity
import com.toquemedia.ekklesia.model.CommunityEntity
import com.toquemedia.ekklesia.model.CommunityMemberType
import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.utils.mocks.CommunityMock

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    community: CommunityEntity,
    members: List<CommunityMemberType> = emptyList(),
    state: ChatUiState
) {
    Scaffold(
        topBar = {
            ChatTopBar(
                communityName = community.communityName.toString(),
                communityImage = community.communityImage.toUri()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
        ) {
            Column {

            }
            Spacer(modifier = Modifier.weight(1f))

            ChatInputMessage(
                value = "",
                onChangeValue = {}
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ChatScreenPrev() {
    ChatScreen(
        community = CommunityMock.getAll().first(),
        state = ChatUiState()
    )
}