package com.toquemedia.ekklesia.ui.screens.community

import android.net.Uri
import com.toquemedia.ekklesia.model.CommunityType

data class CommunityUiState(
    val userPhoto: Uri? = null,
    val imageUri: String = "",
    val communityName: String = "",
    val communityDescription: String = "",
    val community: CommunityType? = null,
    val communities: List<CommunityType> = emptyList(),
    val onImageUriChange: (String) -> Unit = {},
    val onCommunityNameChange: (String) -> Unit = {},
    val onCommunityDescriptionChange: (String) -> Unit = {},
    val onUserPhotoChange: (Uri?) -> Unit = {},
)
