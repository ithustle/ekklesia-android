package com.toquemedia.ekklesia.ui.screens.community

import android.net.Uri
import com.toquemedia.ekklesia.model.CommunityEntity

data class CommunityUiState(
    val userPhoto: Uri? = null,
    val imageUri: String = "",
    val communityName: String = "",
    val communityDescription: String = "",
    val community: CommunityEntity? = null,
    val communities: List<CommunityEntity> = emptyList(),
    val onImageUriChange: (String) -> Unit = {},
    val onCommunityNameChange: (String) -> Unit = {},
    val onCommunityDescriptionChange: (String) -> Unit = {},
    val onUserPhotoChange: (Uri?) -> Unit = {},
)
