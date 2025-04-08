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
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.ui.screens.community.feed.FeedCommunityViewModel
import com.toquemedia.ekklesia.ui.screens.community.feed.FeedScreen
import com.toquemedia.ekklesia.ui.screens.home.HomeScreen
import com.toquemedia.ekklesia.ui.screens.home.HomeViewModel

fun NavGraphBuilder.homeNavigation(navController: NavController) {
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
            },
            onNavigateToCommunity = {
                navController.navigateToCommunityFeed(it)
            }
        )
    }

    composable<Screen.CommunityFeed> {stackEntry ->
        navController.previousBackStackEntry?.let {
            val args = stackEntry.toRoute<Screen.CommunityFeed>()

            val communitySharedViewModel = hiltViewModel<HomeViewModel>(it)

            val sharedViewModel = hiltViewModel<FeedCommunityViewModel>(it)
            val stateFeed by sharedViewModel.uiState.collectAsState()

            val appViewModel = LocalAppViewModel.current
            appViewModel.topBarTitle = args.communityName

            val community = communitySharedViewModel.uiState.value.communitiesUserIn.first { it.community?.id == args.communityId }

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