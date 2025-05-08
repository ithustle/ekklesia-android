package com.toquemedia.seedfy.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.toquemedia.seedfy.LocalAppViewModel
import com.toquemedia.seedfy.routes.Screen
import com.toquemedia.seedfy.routes.navigateToFirstScreen
import com.toquemedia.seedfy.ui.screens.login.AuthViewModel
import com.toquemedia.seedfy.ui.screens.login.LoginScreen

fun NavGraphBuilder.loginNavigation(navController: NavController) {
    composable<Screen.Login> {

        val viewModel = hiltViewModel<AuthViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        val appViewModel = LocalAppViewModel.current

        LoginScreen(
            state = uiState,
            onClickLogin = {
                viewModel.signIn(appViewModel.activityContext)
            },
            onUserAuthenticated = {
                appViewModel.setCurrentUser(it)
            }
        )
    }
}

fun NavController.navigateToLogin() {
    this.navigateToFirstScreen()
}