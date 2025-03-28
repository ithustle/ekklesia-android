package com.toquemedia.ekklesia.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserType(
    val id: String = "",
    val displayName: String? = null,
    val email: String? = null,
    val photo: String? = null
): Parcelable
