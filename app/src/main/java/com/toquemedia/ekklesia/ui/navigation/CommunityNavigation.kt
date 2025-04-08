package com.toquemedia.ekklesia.ui.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.extension.toCommunity
import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.routes.navigateBetweenScreens
import com.toquemedia.ekklesia.ui.composables.ScreenAppLoading
import com.toquemedia.ekklesia.ui.screens.community.CommunityViewModel
import com.toquemedia.ekklesia.ui.screens.community.chat.ChatScreen
import com.toquemedia.ekklesia.ui.screens.community.chat.ChatViewModel
import com.toquemedia.ekklesia.ui.screens.community.create.CreateCommunityScreen
import com.toquemedia.ekklesia.ui.screens.community.feed.FeedCommunityViewModel
import com.toquemedia.ekklesia.ui.screens.community.feed.FeedPostAddComment
import com.toquemedia.ekklesia.ui.screens.community.feed.FeedScreen
import com.toquemedia.ekklesia.ui.screens.community.list.CommunityListScreen

fun NavGraphBuilder.communityNavigation(navController: NavController) {
    composable<Screen.Communities> {

        val appViewModel = LocalAppViewModel.current
        val viewModel = hiltViewModel<CommunityViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        val context = LocalContext.current

        appViewModel.topBarTitle = stringResource(R.string.community)
        //appViewModel.showBackgroundOverlay = uiState.openDialog

        CommunityListScreen(
            onOpenToCreateCommunity = { navController.navigateToCreateCommunity() },
            onNavigateToCommunity = {
                it.community?.toCommunity(context)?.let {
                    navController.navigateToCommunityFeed(community = it)
                }
            },
            //onOpenDialog = { appViewModel.showBackgroundOverlay = true },
            onDismissDialog = { appViewModel.showBackgroundOverlay = false },
            onDeleteCommunity = {
                viewModel.deleteCommunity(it)
                appViewModel.showBackgroundOverlay = false
            },
            state = uiState
        )
    }

    composable<Screen.CreateCommunity> {

        val viewModel = hiltViewModel<CommunityViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val appViewModel = LocalAppViewModel.current

        appViewModel.topBarTitle = stringResource(R.string.newCommunity)

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

    composable<Screen.CommunityFeed> { stackEntry ->

        val parentEntry = remember(navController.currentBackStackEntry) {
            navController.getBackStackEntry(Screen.Communities)
        }

        val sharedViewModel = hiltViewModel<CommunityViewModel>(parentEntry)

        val appViewModel = LocalAppViewModel.current
        val user = appViewModel.currentUser

        val viewModel = hiltViewModel<FeedCommunityViewModel>()
        val state by viewModel.uiState.collectAsState()
        val args = stackEntry.toRoute<Screen.CommunityFeed>()

        appViewModel.topBarTitle = args.communityName

        val community = sharedViewModel.getCurrentCommunity(args.communityId)

        LaunchedEffect(args.communityId) {
            viewModel.getAllPosts(args.communityId)
        }

        community?.let {
            FeedScreen(
                community = community,
                state = state,
                user = user,
                onNavigateToComments = {
                    navController.navigateToAddCommentOnPost(postId = it)
                },
                onLikePost = {
                    viewModel.likeAPost(post = it)
                },
                onRemoveLikePost = {
                    viewModel.likeAPost(post = it, isRemoving = true)
                }
                /*onNavigateToChat = {
                    navController.navigateToChatScreen(community = community)
                } */
            )
        } ?: run {
            ScreenAppLoading(
                screenText = stringResource(R.string.loading_community)
            )
        }
    }

    composable<Screen.CommentPost> { stackEntry ->

        navController.previousBackStackEntry?.let {
            val arg = stackEntry.toRoute<Screen.CommentPost>()

            val sharedViewModel = hiltViewModel<FeedCommunityViewModel>(it)
            val state by sharedViewModel.uiState.collectAsState()

            LaunchedEffect(arg.postId) {
                sharedViewModel.selectPost(arg.postId)
            }

            FeedPostAddComment(
                state = state,
                onSendComment = {
                    sharedViewModel.addCommentOnPost()
                }
            )
        }
    }

    composable<Screen.Chat> { stackEntry ->

        val stackParent = remember(navController.currentBackStackEntry) {
            navController.getBackStackEntry(Screen.Communities)
        }

        val sharedViewModel = hiltViewModel<CommunityViewModel>(stackParent)

        val viewModel = hiltViewModel<ChatViewModel>()
        val state by viewModel.uiState.collectAsState()
        val arg = stackEntry.toRoute<Screen.Chat>()

        val community = sharedViewModel.getCurrentCommunity(arg.communityId)

        community?.let {
            ChatScreen(
                data = it,
                state = state
            )
        } ?: run {
            ScreenAppLoading(
                screenText = stringResource(R.string.loading_community_messages)
            )
        }
    }
}

fun NavController.navigateToCreateCommunity() = this.navigateBetweenScreens(Screen.CreateCommunity)
fun NavController.navigateToCommunityFeed(community: CommunityType) {
    this.navigateBetweenScreens(Screen.CommunityFeed(
        communityId = community.id,
        communityName = community.communityName
    ))
}
fun NavController.navigateToChatScreen(community: CommunityType) = this.navigateBetweenScreens(Screen.Chat(communityId = community.id))
fun NavController.navigateToAddCommentOnPost(postId: String) = this.navigateBetweenScreens(Screen.CommentPost(postId))