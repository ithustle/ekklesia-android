package com.toquemedia.ekklesia.ui.navigation

import android.widget.Toast
import androidx.activity.ComponentActivity
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
import androidx.core.net.toUri
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
import com.toquemedia.ekklesia.ui.screens.community.feed.worshipPost.VideoPlayer
import com.toquemedia.ekklesia.ui.screens.community.feed.worshipPost.VideoPlayerViewModel
import com.toquemedia.ekklesia.ui.screens.community.feed.worshipPost.WorshipPostScreen
import com.toquemedia.ekklesia.ui.screens.community.list.CommunityListScreen
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

fun NavGraphBuilder.communityNavigation(navController: NavController) {
    composable<Screen.Communities> {

        val appViewModel = LocalAppViewModel.current
        val activity = appViewModel.activityContext as ComponentActivity
        val viewModel = hiltViewModel<CommunityViewModel>(activity)
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        val currentUser = appViewModel.currentUser.value

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
                appViewModel.selectedCommunity = it
                navController.navigateToCommunityFeed()
            },
            onDismissDialog = { appViewModel.showBackgroundOverlay = false },
            onDeleteCommunity = {
                viewModel.deleteCommunity(it)
                appViewModel.showBackgroundOverlay = false
            },
            myCommunities = uiState.myCommunities,
            openDialog = uiState.openDialog,
            onOpenDialogChange = {
                appViewModel.showBackgroundOverlay = it
                uiState.onOpenDialogChange(it)
            }
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

        val viewModel = hiltViewModel<FeedCommunityViewModel>(stackEntry)
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        val context = LocalContext.current
        val appViewModel = LocalAppViewModel.current
        val user = appViewModel.currentUser.value

        val selectedCommunity = remember(appViewModel.selectedCommunity) { appViewModel.selectedCommunity }

        LaunchedEffect(state.posts) {
            if (selectedCommunity != null && state.posts.isEmpty()) {
                viewModel.getAllPosts(selectedCommunity.community.id)
            }
        }

        LaunchedEffect(Unit) {
            appViewModel.showTopBar = true
            appViewModel.updateTopBarState(
                newState = TopBarState(
                    title = selectedCommunity?.community?.communityName.toString(),
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

        selectedCommunity?.let { community ->
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

        val arg = stackEntry.toRoute<Screen.CommentPost>()
        val parentEntry = navController.getBackStackEntry(Screen.CommunityFeed)
        val appViewModel = LocalAppViewModel.current

        val sharedViewModel = hiltViewModel<FeedCommunityViewModel>(parentEntry)
        val state by sharedViewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = arg.postId, key2 = state.posts) {
            if (state.posts.isNotEmpty()) {
                sharedViewModel.selectPost(arg.postId)
            }
        }

        state.selectedPost?.let { post ->
            post.worship?.let {

                LaunchedEffect(Unit) {
                    appViewModel.showTopBar = false
                }

                WorshipPostScreen(
                    post = post,
                    onNavigateToVideo = {
                        navController.navigateToPlayer(videoUrl = it)
                    }
                )
            } ?: run {
                FeedPostAddComment(
                    textComment = state.textComment,
                    selectedPost = state.selectedPost,
                    onLikePost = {
                        sharedViewModel.likeAPost(post = it, communityId = arg.communityId)
                    },
                    onChangeTextComment = state.onChangeTextComment,
                    onSendComment = {
                        sharedViewModel.addCommentOnPost(communityId = arg.communityId)
                    }
                )
            }
        }
    }

    composable<Screen.VideoPlayer> {

        val appViewModel = LocalAppViewModel.current
        val arg = it.toRoute<Screen.VideoPlayer>()
        val video = arg.videoUrl.toUri()

        val parentEntry = navController.getBackStackEntry(Screen.CommunityFeed)
        val sharedViewModel = hiltViewModel<FeedCommunityViewModel>(parentEntry)
        val state by sharedViewModel.uiState.collectAsStateWithLifecycle()

        val viewModel = hiltViewModel<VideoPlayerViewModel>(it)
        val playerState by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            appViewModel.showTopBar = false
            viewModel.prepareVideo(video)
            viewModel.playVideo()
        }

        state.selectedPost?.let {
            playerState.player?.let { player ->
                VideoPlayer(
                    post = it,
                    player = player,
                    onPlay = {

                    },
                    onPause = {

                    }
                )
            }
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
fun NavController.navigateToCommunityFeed() = this.navigateBetweenScreens(Screen.CommunityFeed)
fun NavController.navigateToPlayer(videoUrl: String) = this.navigateBetweenScreens(Screen.VideoPlayer(videoUrl = videoUrl))

fun NavController.navigateToChatScreen(community: CommunityType) =
    this.navigateBetweenScreens(Screen.Chat(communityId = community.id))

fun NavController.navigateToAddCommentOnPost(postId: String, communityId: String) =
    this.navigateBetweenScreens(Screen.CommentPost(postId, communityId))