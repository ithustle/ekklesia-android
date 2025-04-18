package com.toquemedia.ekklesia.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.routes.navigateBetweenScreens
import com.toquemedia.ekklesia.ui.screens.login.AuthViewModel
import com.toquemedia.ekklesia.ui.screens.profile.ProfileScreen

fun NavGraphBuilder.profileNavigation(navController: NavController) {
    composable<Screen.Profile> {

        val viewModel = hiltViewModel<AuthViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        uiState.user?.let {
            ProfileScreen(
                user = it,
                onSignOut = {
                    viewModel.signOut()
                    navController.navigateToLogin()
                }
            )
        }
    }
}

fun NavController.navigateToProfile() {
    this.navigateBetweenScreens(Screen.Profile)
}