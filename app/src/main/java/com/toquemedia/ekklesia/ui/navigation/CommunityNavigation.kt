package com.toquemedia.ekklesia.ui.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.model.BottomBarItem
import com.toquemedia.ekklesia.model.Screen
import com.toquemedia.ekklesia.ui.screens.community.CommunityListScreen
import com.toquemedia.ekklesia.ui.screens.community.CommunityViewModel
import com.toquemedia.ekklesia.ui.screens.community.CreateCommunityScreen
import com.toquemedia.ekklesia.routes.navigateBetweenTabs

fun NavGraphBuilder.communityNavigation(navController: NavController) {
    composable(BottomBarItem.Community.route) {

        val viewModel = hiltViewModel<CommunityViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CommunityListScreen(
            onOpenToCreateCommunity = {
                navController.navigateToCreateCommunity()
            },
            onNavigateToProfile = {
                navController.navigateToProfile()
            },
            state = uiState
        )
    }

    composable(Screen.CreateCommunity.route) {

        val viewModel = hiltViewModel<CommunityViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        val launcherCommunityPhoto =
            rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
                it?.let {
                    uiState.onImageUriChange(it.toString())
                }
            }

        CreateCommunityScreen(
            state = uiState,
            validation = viewModel.validationEvent,
            onTapToLoadImageOnLibrary = {
                launcherCommunityPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            onCreateCommunity = {
                viewModel.createCommunity()
            },
            onCreateSuccessfulCommunity = {
                navController.popBackStack()
            }
        )
    }
}

fun NavController.navigateToCommunity() {
    this.navigateBetweenTabs(BottomBarItem.Community.route)
}

fun NavController.navigateToCreateCommunity() {
    this.navigateBetweenTabs(Screen.CreateCommunity.route)
}