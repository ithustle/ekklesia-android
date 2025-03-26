package com.toquemedia.ekklesia.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.routes.navigateBetweenTabs
import com.toquemedia.ekklesia.ui.screens.home.HomeScreen
import com.toquemedia.ekklesia.ui.screens.home.HomeViewModel

fun NavGraphBuilder.homeNavigation() {
    composable<Screen.Home> {

        val currentUser = LocalAppViewModel.current.currentUser
        val viewModel = hiltViewModel<HomeViewModel>()
        val state by viewModel.uiState.collectAsState()

        HomeScreen(
            state = state,
            currentUser = currentUser
        )
    }
}

fun NavHostController.navigateToHome() {
    this.navigateBetweenTabs(Screen.Home)
}