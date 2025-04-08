package com.toquemedia.ekklesia.ui.screens.community.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.toquemedia.ekklesia.model.CommunityWithMembers
import com.toquemedia.ekklesia.utils.mocks.CommunityMock

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    data: CommunityWithMembers,
    state: ChatUiState
) {
    Scaffold(
        topBar = {
            ChatTopBar(
                communityName = data.community?.communityName.toString(),
                communityImage = data.community?.communityImage?.toUri()
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
                inputFor = ChatInputMessage.CHAT,
                onChangeValue = {}
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ChatScreenPrev() {
    ChatScreen(
        data = CommunityMock.getAllCommunityWithMembers().first(),
        state = ChatUiState()
    )
}