package com.toquemedia.ekklesia.model

import android.net.Uri

data class UserType(
    val id: String,
    val displayName: String?,
    val email: String?,
    val photo: Uri?
)
