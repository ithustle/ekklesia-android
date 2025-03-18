package com.toquemedia.ekklesia.ui.screens.community

import com.toquemedia.ekklesia.model.CommunityType

data class CommunityUiState(
    val imageUri: String = "",
    val communityName: String = "",
    val communityDescription: String = "",
    val communities: List<CommunityType> = emptyList(),
    val onImageUriChange: (String) -> Unit = {},
    val onCommunityNameChange: (String) -> Unit = {},
    val onCommunityDescriptionChange: (String) -> Unit = {},
)
