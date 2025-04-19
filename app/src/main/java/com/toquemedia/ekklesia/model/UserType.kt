package com.toquemedia.ekklesia.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class UserType(
    val id: String = "",
    val displayName: String? = null,
    val email: String? = null,
    val photo: String? = null,
    val communitiesIn: List<String> = emptyList(),
    val postsLiked: List<String> = emptyList()
)
