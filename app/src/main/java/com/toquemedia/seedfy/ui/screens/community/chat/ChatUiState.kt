package com.toquemedia.seedfy.ui.screens.community.chat

import com.toquemedia.seedfy.model.MessageEntity

data class ChatUiState(
    val messages: List<MessageEntity> = emptyList(),
    val message: MessageEntity? = null,
    val communityId: String = "",
    val onCommunityIdChange: (String) -> Unit = {}
)
