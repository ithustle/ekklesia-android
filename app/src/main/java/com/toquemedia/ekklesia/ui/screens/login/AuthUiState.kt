package com.toquemedia.ekklesia.ui.screens.login

import com.toquemedia.ekklesia.model.UserType

data class AuthUiState(
    val user: UserType? = null,
    val isLoading: Boolean = false,
    val myNote: String = "",
    val onMyNote: (String) -> Unit = {},
    val codeSent: Boolean = false,
    val verificationId: String? = null,
    val error: String? = null,
    val resendCode: (() -> Unit)? = null,
    val needsProfileCompletion: Boolean = false,
    val tempUserId: String? = null
)