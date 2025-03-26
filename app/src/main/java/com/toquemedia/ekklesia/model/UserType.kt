package com.toquemedia.ekklesia.model

import android.net.Uri

data class UserType(
    val id: String,
    val displayName: String? = null,
    val email: String? = null,
    val photo: Uri? = null
)
