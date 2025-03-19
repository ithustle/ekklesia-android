package com.toquemedia.ekklesia.ui.screens.community.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.toquemedia.ekklesia.ui.screens.community.CommunityUiState
import com.toquemedia.ekklesia.utils.mocks.CommunityMock

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    state: CommunityUiState
) {
    Scaffold(
        topBar = {
            ChatTopBar(
                communityName = state.community?.communityName.toString(),
                communityImage = state.community?.communityImage?.toUri()
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

            ChatInputMessage()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatScreenPrev() {
    ChatScreen(
        state = CommunityUiState(community = CommunityMock.getAll().first())
    )
}