package com.toquemedia.seedfy.model

import androidx.compose.runtime.Immutable
import java.util.Date

@Immutable
data class CommunityType(
    val id: String = "",
    val communityName: String = "",
    val communityDescription: String = "",
    val communityImage: String = "",
    val email: String = "",
    val createdAt: Date = Date(),
    val active: Boolean = true
)

@Immutable
data class CommunityMemberType(
    val id: String = "",
    val user: UserType = UserType(id = ""),
    val isAdmin: Boolean = false
)


@Immutable
data class CommunityWithMembers(
    val community: CommunityType,
    val allMembers: List<CommunityMemberType>
)