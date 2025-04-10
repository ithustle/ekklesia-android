package com.toquemedia.ekklesia.ui.navigation

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
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.ui.screens.community.feed.FeedCommunityViewModel
import com.toquemedia.ekklesia.ui.screens.community.feed.FeedScreen
import com.toquemedia.ekklesia.ui.screens.home.HomeScreen
import com.toquemedia.ekklesia.ui.screens.home.HomeViewModel
import java.util.Date

fun NavGraphBuilder.homeNavigation(navController: NavController) {
    composable<Screen.Home> {

        val appViewModel = LocalAppViewModel.current
        val viewModel = hiltViewModel<HomeViewModel>()
        val state by viewModel.uiState.collectAsState()

        val context = LocalContext.current

        LaunchedEffect(Unit) {
            appViewModel.topBarTitle = "${Date().getGreeting()}, ${appViewModel.currentUser?.displayName}"
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
            appViewModel.topBarTitle = args.communityName

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