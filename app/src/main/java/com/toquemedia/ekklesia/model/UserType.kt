package com.toquemedia.ekklesia.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class UserType(
    val id: String = "",
    val displayName: String? = null,
    val email: String? = null,
    val photo: String? = null
): Parcelable
