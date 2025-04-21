package com.toquemedia.ekklesia.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.routes.navigateToFirstScreen
import com.toquemedia.ekklesia.routes.navigateToHomeGraph
import com.toquemedia.ekklesia.ui.screens.login.AuthViewModel
import com.toquemedia.ekklesia.ui.screens.login.LoginScreen

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
                appViewModel.currentUser = it
                navController.navigateToHomeGraph()
            }
        )
    }
}

fun NavController.navigateToLogin() {
    this.navigateToFirstScreen()
}