package com.toquemedia.ekklesia.model

import androidx.compose.runtime.Immutable

@Immutable
data class UserType(
    val id: String = "",
    val displayName: String? = null,
    val email: String? = null,
    val photo: String? = null,
    val communitiesIn: List<String> = emptyList()
)
