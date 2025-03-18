package com.toquemedia.ekklesia.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.model.BottomBarItem
import com.toquemedia.ekklesia.routes.navigateToBibleGraph
import com.toquemedia.ekklesia.routes.navigateToFirstScreen
import com.toquemedia.ekklesia.ui.screens.login.AuthViewModel
import com.toquemedia.ekklesia.ui.screens.login.LoginScreen

fun NavGraphBuilder.loginNavigation(navController: NavController) {
    composable(BottomBarItem.Login.route) {

        val viewModel = hiltViewModel<AuthViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        LoginScreen(
            state = uiState,
            onClickLogin = {
                viewModel.signIn()
            },
            onUserAuthenticated = {
                navController.navigateToBibleGraph()
            }
        )
    }
}

fun NavController.navigateToLogin() {
    this.navigateToFirstScreen(BottomBarItem.Login.route)
}