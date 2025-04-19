package com.toquemedia.ekklesia.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.model.TopBarState
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.routes.navigateBetweenScreens
import com.toquemedia.ekklesia.ui.screens.login.AuthViewModel
import com.toquemedia.ekklesia.ui.screens.profile.ProfileScreen

fun NavGraphBuilder.profileNavigation(navController: NavController) {
    composable<Screen.Profile> {

        val viewModel = hiltViewModel<AuthViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        val appViewModel = LocalAppViewModel.current

        LaunchedEffect(Unit) {
            appViewModel.updateTopBarState(
                newState = TopBarState(
                    title = "Perfil",
                    showBackButton = true,
                    isBackgroundTransparent = true,
                    onBackNavigation = {
                        navController.popBackStack()
                    }
                )
            )
        }

        uiState.user?.let {
            ProfileScreen(
                user = it,
                onSignOut = {
                    viewModel.signOut()
                    appViewModel.currentUser = null
                    navController.navigateToLogin()
                }
            )
        }
    }
}

fun NavController.navigateToProfile() {
    this.navigateBetweenScreens(Screen.Profile)
}