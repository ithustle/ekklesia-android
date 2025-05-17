package com.toquemedia.seedfy.ui.navigation

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.toquemedia.seedfy.LocalAppViewModel
import com.toquemedia.seedfy.extension.getGreeting
import com.toquemedia.seedfy.model.TopBarState
import com.toquemedia.seedfy.routes.Screen
import com.toquemedia.seedfy.ui.composables.EkklesiaNoInternet
import com.toquemedia.seedfy.ui.screens.community.CommunityViewModel
import com.toquemedia.seedfy.ui.screens.home.HomeScreen
import com.toquemedia.seedfy.ui.screens.home.HomeViewModel
import com.toquemedia.seedfy.utils.mocks.BitmapUtil
import java.util.Date

fun NavGraphBuilder.homeNavigation(navController: NavController) {
    composable<Screen.Home> {

        val appViewModel = LocalAppViewModel.current
        val activity = appViewModel.activityContext as ComponentActivity

        val communityViewModel = hiltViewModel<CommunityViewModel>(activity)
        val viewModel = hiltViewModel<HomeViewModel>()

        val communityState by communityViewModel.uiState.collectAsStateWithLifecycle()
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        val context = LocalContext.current
        val currentUser = appViewModel.currentUser.value

        val shareLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                viewModel.shareVerseOfDay()
            }

        LaunchedEffect(Unit) {
            appViewModel.updateTopBarState(
                newState = TopBarState(
                    title = "${Date().getGreeting()}, ${currentUser?.displayName}",
                    useAvatar = currentUser?.photo
                )
            )
        }

        println("communityState.newCommunity: ${communityState.newCommunity}")

        produceState(false, communityState.newCommunity) {
            if (value) {
                communityState.newCommunity?.let {
                    //appViewModel.selectedCommunity = it
                    communityState.selectedCommunity = it
                    navController.navigateToCommunityFeed()
                }
            }
            value = true
        }

        state.errorConnection?.let {
            EkklesiaNoInternet(
                message = it,
                loading = communityState.loadingCommunitiesUserIn,
                onTryAgain = {
                    viewModel.loadingHome()
                    communityViewModel.loadCommunities()
                }
            )
        } ?: run {
            HomeScreen(
                communitiesUserIn = communityState.communitiesUserIn,
                loadingCommunitiesUserIn = communityState.loadingCommunitiesUserIn,
                communities = communityState.communities,
                loadCommunities = communityState.loadCommunities,
                joiningToCommunity = communityState.joiningToCommunity,
                verseOfDay = state.verseOfDay,
                verseOfDayStats = state.verseOfDayStats,
                likedVerseOfDay = state.likedVerseOfDay,
                onJoinToCommunity = communityViewModel::joinToCommunity,
                onNavigateToCommunity = {
                    //appViewModel.selectedCommunity = it
                    communityState.selectedCommunity = it
                    navController.navigateToCommunityFeed()
                },
                onLikeVerseOfDay = viewModel::handleLikeVerseOfDay,
                onShareVerseOfDay = {
                    BitmapUtil.shareVerseBitmap(context, shareLauncher, state.verseOfDay)
                }
            )
        }
    }
}