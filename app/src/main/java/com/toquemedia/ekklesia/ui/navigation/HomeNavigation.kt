package com.toquemedia.ekklesia.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.model.BottomBarItem
import com.toquemedia.ekklesia.routes.navigateBetweenTabs
import com.toquemedia.ekklesia.ui.screens.home.HomeScreen
import com.toquemedia.ekklesia.ui.screens.home.HomeViewModel

fun NavGraphBuilder.homeNavigation() {
    composable(BottomBarItem.Home.route) {

        val viewModel = hiltViewModel<HomeViewModel>()
        val state by viewModel.uiState.collectAsState()

        HomeScreen(
            state = state,
        )
    }
}

fun NavHostController.navigateToHome() {
    this.navigateBetweenTabs(BottomBarItem.Home.route)
}