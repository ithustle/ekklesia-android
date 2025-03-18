package com.toquemedia.ekklesia.ui.screens.login

import com.toquemedia.ekklesia.model.UserType

data class AuthUiState(
    val user: UserType? = null,
    val isLoading: Boolean = false
)
