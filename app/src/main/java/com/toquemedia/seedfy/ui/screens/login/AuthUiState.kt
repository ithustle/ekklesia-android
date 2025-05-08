package com.toquemedia.seedfy.ui.screens.login

import com.toquemedia.seedfy.model.UserType

data class AuthUiState(
    val user: UserType? = null,
    val isLoading: Boolean = false
)
