package com.toquemedia.ekklesia.ui.screens.community

import android.net.Uri
import androidx.compose.runtime.mutableStateMapOf
import com.toquemedia.ekklesia.model.CommunityEntity
import com.toquemedia.ekklesia.model.CommunityMemberType
import com.toquemedia.ekklesia.model.CommunityWithMembers

data class CommunityUiState(
    val userPhoto: Uri? = null,
    val imageUri: String = "",
    val communityName: String = "",
    val communityDescription: String = "",
    val community: CommunityEntity? = null,
    val openDialog: Boolean = false,
    val members: List<CommunityMemberType> = emptyList(),
    val communities: List<CommunityEntity> = emptyList(),
    val onImageUriChange: (String) -> Unit = {},
    val onCommunityNameChange: (String) -> Unit = {},
    val onCommunityDescriptionChange: (String) -> Unit = {},
    val onUserPhotoChange: (Uri?) -> Unit = {},
    val onOpenDialogChange: (Boolean) -> Unit = {},
)
