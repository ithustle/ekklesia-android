package com.toquemedia.ekklesia.ui.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.extension.getGreeting
import com.toquemedia.ekklesia.model.TopBarState
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.ui.screens.community.feed.FeedCommunityViewModel
import com.toquemedia.ekklesia.ui.screens.community.feed.FeedScreen
import com.toquemedia.ekklesia.ui.screens.home.HomeScreen
import com.toquemedia.ekklesia.ui.screens.home.HomeViewModel
import com.toquemedia.ekklesia.utils.mocks.BitmapUtil
import java.util.Date

fun NavGraphBuilder.homeNavigation(navController: NavController) {
    composable<Screen.Home> {

        val appViewModel = LocalAppViewModel.current
        val viewModel = hiltViewModel<HomeViewModel>()
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

        println("Communities: ${state.communities.size}")

        HomeScreen(
            communitiesUserIn = state.communitiesUserIn,
            loadingCommunitiesUserIn = state.loadingCommunitiesUserIn,
            communities = state.communities,
            loadCommunities = state.loadCommunities,
            joiningToCommunity = state.joiningToCommunity,
            verseOfDay = state.verseOfDay,
            verseOfDayStats = state.verseOfDayStats,
            likedVerseOfDay = state.likedVerseOfDay,
            context = context,
            onJoinToCommunity = viewModel::joinToCommunity,
            onNavigateToCommunity = {
                navController.navigateToCommunityFeed(it)
            },
            onLikeVerseOfDay = viewModel::handleLikeVerseOfDay,
            onShareVerseOfDay = {
                BitmapUtil.shareVerseBitmap(context, shareLauncher, state.verseOfDay)
            }
        )
    }

    composable<Screen.CommunityFeed> {stackEntry ->
        navController.previousBackStackEntry?.let {
            val args = stackEntry.toRoute<Screen.CommunityFeed>()

            val communitySharedViewModel = hiltViewModel<HomeViewModel>(it)
            val stateHome by communitySharedViewModel.uiState.collectAsState()

            val sharedViewModel = hiltViewModel<FeedCommunityViewModel>(it)
            val stateFeed by sharedViewModel.uiState.collectAsState()

            val appViewModel = LocalAppViewModel.current

            val community = stateHome.communitiesUserIn.first { it.community?.id == args.communityId }

            LaunchedEffect(args.communityId) {
                sharedViewModel.getAllPosts(args.communityId)
            }

            FeedScreen(
                community = community,
                state = stateFeed,
                user = appViewModel.currentUser,
                onNavigateToComments = {
                    navController.navigateToAddCommentOnPost(postId = it)
                },
                onLikePost = {
                    sharedViewModel.likeAPost(post = it)
                },
                onRemoveLikePost = {
                    sharedViewModel.likeAPost(post = it, isRemoving = true)
                }
                /*onNavigateToChat = {
                    navController.navigateToChatScreen(community = community)
                } */
            )
        }
    }
}