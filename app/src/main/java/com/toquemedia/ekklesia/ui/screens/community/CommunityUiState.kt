package com.toquemedia.ekklesia.ui.screens.community

import android.net.Uri
import com.toquemedia.ekklesia.model.CommunityMemberType
import com.toquemedia.ekklesia.model.CommunityWithMembers

data class CommunityUiState(
    val userPhoto: Uri? = null,
    val imageUri: String = "",
    val communityName: String = "",
    val communityDescription: String = "",
    val community: CommunityWithMembers? = null,
    val openDialog: Boolean = false,
    val members: List<CommunityMemberType> = emptyList(),
    val communities: List<CommunityWithMembers> = emptyList(),
    val onImageUriChange: (String) -> Unit = {},
    val onCommunityNameChange: (String) -> Unit = {},
    val onCommunityDescriptionChange: (String) -> Unit = {},
    val onUserPhotoChange: (Uri?) -> Unit = {},
    val onOpenDialogChange: (Boolean) -> Unit = {},
    val communitiesUserIn: List<CommunityWithMembers> = emptyList(),
    val loadingCommunitiesUserIn: Boolean = false,
    val loadCommunities: Boolean = false,
    val joiningToCommunity: Boolean = false,
)
