package com.toquemedia.ekklesia.ui.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.extension.getGreeting
import com.toquemedia.ekklesia.model.TopBarState
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.ui.screens.community.CommunityViewModel
import com.toquemedia.ekklesia.ui.screens.home.HomeScreen
import com.toquemedia.ekklesia.ui.screens.home.HomeViewModel
import com.toquemedia.ekklesia.utils.mocks.BitmapUtil
import java.util.Date

fun NavGraphBuilder.homeNavigation(navController: NavController) {
    composable<Screen.Home> {

        val appViewModel = LocalAppViewModel.current

        val communityViewModel = hiltViewModel<CommunityViewModel>()
        val viewModel = hiltViewModel<HomeViewModel>()

        val communityState by communityViewModel.uiState.collectAsState()
        val state by viewModel.uiState.collectAsState()

        val context = LocalContext.current
        val currentUser = appViewModel.currentUser

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
                    navController.navigateToCommunityFeed(it.community)
                }
            }
            value = true
        }

        println("Communities: ${communityState.communities.size}")

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
                navController.navigateToCommunityFeed(it)
            },
            onLikeVerseOfDay = viewModel::handleLikeVerseOfDay,
            onShareVerseOfDay = {
                BitmapUtil.shareVerseBitmap(context, shareLauncher, state.verseOfDay)
            }
        )
    }
}