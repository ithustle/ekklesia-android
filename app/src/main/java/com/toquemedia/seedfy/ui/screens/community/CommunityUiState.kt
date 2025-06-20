package com.toquemedia.seedfy.ui.screens.community

import android.net.Uri
import com.toquemedia.seedfy.model.CommunityMemberType
import com.toquemedia.seedfy.model.CommunityWithMembers

data class CommunityUiState(
    val userPhoto: Uri? = null,
    val imageUri: Uri? = null,
    val communityName: String = "",
    val communityDescription: String = "",
    var selectedCommunity: CommunityWithMembers? = null,
    val openDialog: Boolean = false,
    val loadingLeftCommunity: Boolean = false,
    val members: List<CommunityMemberType> = emptyList(),
    val communities: List<CommunityWithMembers> = emptyList(),
    val myCommunities: List<CommunityWithMembers> = emptyList(),
    val newCommunity: CommunityWithMembers? = null,
    val onImageUriChange: (Uri) -> Unit = {},
    val onCommunityNameChange: (String) -> Unit = {},
    val onCommunityDescriptionChange: (String) -> Unit = {},
    val onUserPhotoChange: (Uri?) -> Unit = {},
    val onOpenDialogChange: (Boolean) -> Unit = {},
    var communitiesUserIn: List<CommunityWithMembers> = emptyList(),
    val loadingCommunitiesUserIn: Boolean = true,
    val loadCommunities: Boolean = true,
    val joiningToCommunity: Boolean = false,
)
