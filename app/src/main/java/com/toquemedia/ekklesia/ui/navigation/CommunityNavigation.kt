package com.toquemedia.ekklesia.ui.navigation

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.model.TopBarState
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
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

fun NavGraphBuilder.communityNavigation(navController: NavController) {
    composable<Screen.Communities> {

        val stackEntry = remember(navController.currentBackStackEntry) {
            navController.getBackStackEntry(Screen.Home)
        }

        val appViewModel = LocalAppViewModel.current
        val viewModel = hiltViewModel<CommunityViewModel>(stackEntry)
        val uiState by viewModel.uiState.collectAsState()

        val currentUser = appViewModel.currentUser

        //appViewModel.showBackgroundOverlay = uiState.openDialog

        LaunchedEffect(Unit) {
            appViewModel.updateTopBarState(
                newState = TopBarState(
                    title = "Minhas comunidades",
                    useAvatar = currentUser?.photo
                )
            )
        }

        CommunityListScreen(
            onOpenToCreateCommunity = { navController.navigateToCreateCommunity() },
            onNavigateToCommunity = {
                navController.navigateToCommunityFeed(community = it.community)
            },
            //onOpenDialog = { appViewModel.showBackgroundOverlay = true },
            onDismissDialog = { appViewModel.showBackgroundOverlay = false },
            onDeleteCommunity = {
                viewModel.deleteCommunity(it)
                appViewModel.showBackgroundOverlay = false
            },
            myCommunities = uiState.myCommunities.filter { it.community.email == currentUser?.email },
            openDialog = uiState.openDialog,
            onOpenDialogChange = uiState.onOpenDialogChange
        )
    }

    composable<Screen.CreateCommunity> {
        navController.previousBackStackEntry?.let { stackEntry ->
            val viewModel = hiltViewModel<CommunityViewModel>(stackEntry)
            val uiState by viewModel.uiState.collectAsState()

            val appViewModel = LocalAppViewModel.current

            val launcherCommunityPhoto =
                rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
                    it?.let {
                        uiState.onImageUriChange(it)
                    }
                }

            LaunchedEffect(Unit) {
                appViewModel.updateTopBarState(
                    newState = TopBarState(
                        title = "Nova comunidade",
                        showBackButton = true,
                        onBackNavigation = {
                            navController.popBackStack()
                        }
                    )
                )
            }

            CreateCommunityScreen(
                state = uiState,
                validation = viewModel.validationEvent,
                onTapToLoadImageOnLibrary = {
                    launcherCommunityPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                onCreateCommunity = viewModel::createCommunity,
                onCreateSuccessfulCommunity = {
                    navController.popBackStack()
                }
            )
        }
    }

    composable<Screen.CommunityFeed> { stackEntry ->

        val parentEntry = remember(navController.currentBackStackEntry) {
            navController.getBackStackEntry(Screen.Home)
        }
        println("Rola aqui")
        println("parentEntry: $parentEntry")

        val context = LocalContext.current
        val appViewModel = LocalAppViewModel.current
        val user = appViewModel.currentUser

        val viewModel = hiltViewModel<FeedCommunityViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val args = stackEntry.toRoute<Screen.CommunityFeed>()

        val sharedViewModel = hiltViewModel<CommunityViewModel>(parentEntry)

        val community = remember(args.communityId) {
            sharedViewModel.getCurrentCommunity(
                args.communityId,
            )
        }

        LaunchedEffect(args.communityId, state.posts) {
            if (community != null && state.posts.isEmpty()) {
                println("LaunchedEffect: Carregando posts para communityId: ${args.communityId}")
                viewModel.getAllPosts(args.communityId)
            }
        }

        LaunchedEffect(Unit) {
            appViewModel.updateTopBarState(
                newState = TopBarState(
                    title = community?.community?.communityName.toString(),
                    showTitleAvatar = true,
                    showBackButton = true,
                    useAvatar = user?.photo,
                    actions = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Chat,
                            contentDescription = stringResource(R.string.change_community_description),
                            tint = PrincipalColor,
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .size(20.dp)
                                .clickable {
                                    Toast.makeText(
                                        context,
                                        "Funcionalidade em desenvolvimento",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        )
                    },
                    onBackNavigation = {
                        navController.popBackStack()
                    }
                )
            )
        }

        community?.let { community ->
            FeedScreen(
                community = community,
                posts = state.posts,
                likedPosts = state.likedPosts,
                loadingPosts = state.loadingPosts,
                user = user,
                onNavigateToComments = {
                    navController.navigateToAddCommentOnPost(postId = it, communityId = community.community.id)
                },
                onLikePost = {
                    viewModel.likeAPost(post = it, communityId = community.community.id)
                },
                onRemoveLikePost = {
                    viewModel.likeAPost(post = it, communityId = community.community.id, isRemoving = true)
                },
                onAddStory = {
                    Toast.makeText(context, "Funcionalidade em desenvolvimento", Toast.LENGTH_SHORT)
                        .show()
                    //navController.navigateToChatScreen(community = community)
                }
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
                    sharedViewModel.addCommentOnPost(communityId = arg.communityId)
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

        val community =
            sharedViewModel.getCurrentCommunity(arg.communityId)

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
    this.navigateBetweenScreens(
        Screen.CommunityFeed(
            communityId = community.id,
            communityName = community.communityName
        )
    )
}

fun NavController.navigateToChatScreen(community: CommunityType) =
    this.navigateBetweenScreens(Screen.Chat(communityId = community.id))

fun NavController.navigateToAddCommentOnPost(postId: String, communityId: String) =
    this.navigateBetweenScreens(Screen.CommentPost(postId, communityId))