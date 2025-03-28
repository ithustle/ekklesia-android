package com.toquemedia.ekklesia.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.ui.screens.home.HomeScreen
import com.toquemedia.ekklesia.ui.screens.home.HomeViewModel

fun NavGraphBuilder.homeNavigation() {
    composable<Screen.Home> {

        val appViewModel = LocalAppViewModel.current
        val viewModel = hiltViewModel<HomeViewModel>()
        val state by viewModel.uiState.collectAsState()

        val context = LocalContext.current

        appViewModel.topBarTitle = "Bom dia, ${appViewModel.currentUser?.displayName}"

        HomeScreen(
            state = state,
            context = context,
            onJoinToCommunity = { communityId ->
                viewModel.joinToCommunity(communityId)
            }
        )
    }
}