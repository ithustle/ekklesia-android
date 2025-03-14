package com.toquemedia.ekklesia.ui.screens.community

data class CommunityUiState(
    val imageUri: String = "",
    val communityName: String = "",
    val communityDescription: String = "",
    val onImageUriChange: (String) -> Unit = {},
    val onCommunityNameChange: (String) -> Unit = {},
    val onCommunityDescriptionChange: (String) -> Unit = {},
)
