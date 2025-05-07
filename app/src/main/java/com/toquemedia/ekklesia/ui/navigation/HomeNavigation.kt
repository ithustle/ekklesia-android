package com.toquemedia.ekklesia.ui.navigation

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.extension.getGreeting
import com.toquemedia.ekklesia.model.TopBarState
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.ui.composables.EkklesiaNoInternet
import com.toquemedia.ekklesia.ui.screens.community.CommunityViewModel
import com.toquemedia.ekklesia.ui.screens.home.HomeScreen
import com.toquemedia.ekklesia.ui.screens.home.HomeViewModel
import com.toquemedia.ekklesia.utils.mocks.BitmapUtil
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

        produceState(false, communityState.newCommunity) {
            if (value) {
                communityState.newCommunity?.let {
                    appViewModel.selectedCommunity = it
                    navController.navigateToCommunityFeed()
                }
            }
            value = true
        }

        state.errorConnection?.let {
            println("communityState.loadingCommunitiesUserIn: ${communityState.loadingCommunitiesUserIn}")
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
                context = context,
                onJoinToCommunity = communityViewModel::joinToCommunity,
                onNavigateToCommunity = {
                    appViewModel.selectedCommunity = it
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