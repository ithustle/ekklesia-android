package com.toquemedia.seedfy.ui.navigation

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.toquemedia.seedfy.LocalAppViewModel
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.model.TopBarState
import com.toquemedia.seedfy.routes.Screen
import com.toquemedia.seedfy.routes.navigateBetweenScreens
import com.toquemedia.seedfy.ui.composables.EkklesiaProgress
import com.toquemedia.seedfy.ui.composables.ScreenAppLoading
import com.toquemedia.seedfy.ui.screens.community.CommunityViewModel
import com.toquemedia.seedfy.ui.screens.community.chat.ChatScreen
import com.toquemedia.seedfy.ui.screens.community.chat.ChatViewModel
import com.toquemedia.seedfy.ui.screens.community.create.CreateCommunityScreen
import com.toquemedia.seedfy.ui.screens.community.details.CommunityDetailScreen
import com.toquemedia.seedfy.ui.screens.community.feed.FeedCommunityViewModel
import com.toquemedia.seedfy.ui.screens.community.feed.FeedPostAddComment
import com.toquemedia.seedfy.ui.screens.community.feed.FeedScreen
import com.toquemedia.seedfy.ui.screens.community.feed.story.StoriesViewScreen
import com.toquemedia.seedfy.ui.screens.community.feed.worshipPost.VideoPlayer
import com.toquemedia.seedfy.ui.screens.community.feed.worshipPost.VideoPlayerViewModel
import com.toquemedia.seedfy.ui.screens.community.feed.worshipPost.WorshipPostScreen
import com.toquemedia.seedfy.ui.screens.community.list.CommunityListScreen
import com.toquemedia.seedfy.ui.theme.PrincipalColor
import kotlinx.coroutines.launch

@OptIn(UnstableApi::class)
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
                //appViewModel.selectedCommunity = it
                uiState.selectedCommunity = it
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

        val appViewModel = LocalAppViewModel.current
        val activity = appViewModel.activityContext as ComponentActivity

        val communityViewModel = hiltViewModel<CommunityViewModel>(activity)
        val communityState by communityViewModel.uiState.collectAsStateWithLifecycle()

        val viewModel = hiltViewModel<FeedCommunityViewModel>(activity)
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        val user = appViewModel.currentUser.value

        //val selectedCommunity = remember(appViewModel.selectedCommunity) { appViewModel.selectedCommunity }
        val selectedCommunity = remember(communityState.selectedCommunity) { communityState.selectedCommunity }

        LaunchedEffect(state.posts) {
            if (selectedCommunity != null && state.posts.isEmpty()) {
                viewModel.getAllPosts(selectedCommunity.community.id)
                viewModel.getStories(selectedCommunity.community.id)
            }
        }

        LaunchedEffect(Unit) {
            appViewModel.showTopBar = true
            appViewModel.updateTopBarState(
                newState = TopBarState(
                    title = selectedCommunity?.community?.communityName.toString(),
                    showTitleAvatar = true,
                    showBackButton = true,
                    useAvatar = null,
                    actions = {
                        Icon(
                            imageVector = Icons.Rounded.Info,
                            contentDescription = stringResource(R.string.change_community_description),
                            tint = PrincipalColor,
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .size(28.dp)
                                .clickable {
                                    navController.navigateToCommunityDetails()
                                }
                        )
                    },
                    onBackNavigation = navController::popBackStack
                )
            )
        }

        selectedCommunity?.let { community ->
            FeedScreen(
                community = community,
                posts = state.posts,
                likedPosts = state.likedPosts,
                loadingPosts = state.loadingPosts,
                onUserLikes = state.onUserLikes,
                user = user,
                stories = state.stories,
                onNavigateToComments = {
                    navController.navigateToAddCommentOnPost(
                        postId = it,
                        communityId = community.community.id
                    )
                },
                onLikePost = {
                    viewModel.likeAPost(post = it, communityId = community.community.id)
                },
                onRemoveLikePost = {
                    viewModel.likeAPost(
                        post = it,
                        communityId = community.community.id,
                        isRemoving = true
                    )
                },
                onShowStory = {
                    navController.navigateToStories(email = it.email.toString())
                },
                onGoBack = {
                    it?.let {
                        println("ID: $it")
                        communityViewModel.removeCommunityFromList(it, user?.email.toString())
                    }
                    navController.popBackStack()
                }
            )
        }
    }

    composable<Screen.CommunityDetails> {

        val appViewModel = LocalAppViewModel.current
        val activity = appViewModel.activityContext as ComponentActivity
        val viewModel = hiltViewModel<CommunityViewModel>(activity)
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        val context = LocalContext.current
        //val communityId = appViewModel.selectedCommunity?.community?.id
        val communityId = state.selectedCommunity?.community?.id
        val user = appViewModel.currentUser.value
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            appViewModel.updateTopBarState(
                newState = TopBarState(
                    showBackButton = true,
                    title = context.getString(R.string.community_detail_title),
                    useAvatar = null,
                    actions = {
                        if (state.loadingLeftCommunity) {
                            EkklesiaProgress(
                                color = PrincipalColor,
                                size = 20.dp,
                                modifier = Modifier
                                    .padding(all = 16.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.Logout,
                                contentDescription = stringResource(R.string.logout_description),
                                tint = PrincipalColor,
                                modifier = Modifier
                                    .padding(horizontal = 12.dp)
                                    .size(20.dp)
                                    .clickable {
                                        appViewModel.showBackgroundOverlay = true
                                        state.onOpenDialogChange(true)
                                    }
                            )
                        }
                    },
                )
            )
        }

        //appViewModel.selectedCommunity?.let {
        state.selectedCommunity?.let {
            CommunityDetailScreen(
                data = it,
                currentUser = user,
                openDialog = state.openDialog,
                onHandleDialog = {
                    appViewModel.showBackgroundOverlay = it
                    state.onOpenDialogChange(it)
                },
                onLeftCommunity = {
                    scope.launch {
                        viewModel.leftCommunity(communityId.toString())
                        val updatedMembers =
                            //appViewModel.selectedCommunity?.allMembers?.filter { it.id != user?.id }
                            state.selectedCommunity?.allMembers?.filter { it.id != user?.id }
                        updatedMembers?.let {
                            //appViewModel.selectedCommunity = appViewModel.selectedCommunity?.copy(
                            state.selectedCommunity = state.selectedCommunity?.copy(
                                allMembers = it
                            )
                        }
                        appViewModel.showBackgroundOverlay = false
                        navController.popBackStack()
                    }
                },
            )
        }
    }

    composable<Screen.StoriesNavigation> {

        val appViewModel = LocalAppViewModel.current
        //val parentEntry = navController.getBackStackEntry(Screen.CommunityFeed)
        val activity = appViewModel.activityContext as ComponentActivity

        val arg = it.toRoute<Screen.StoriesNavigation>()

        val sharedViewModel = hiltViewModel<FeedCommunityViewModel>(activity)
        val state by sharedViewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            appViewModel.showTopBar = false
        }

        StoriesViewScreen(
            stories = state.stories.filter { it.user?.email == arg.email }
                .sortedBy { it.createdAt },
            onFinishStory = {
                navController.popBackStack()
            }
        )
    }

    composable<Screen.CommentPost> { stackEntry ->

        val arg = stackEntry.toRoute<Screen.CommentPost>()
        //val parentEntry = navController.getBackStackEntry(Screen.CommunityFeed)
        val appViewModel = LocalAppViewModel.current
        val activity = appViewModel.activityContext as ComponentActivity

        val sharedViewModel = hiltViewModel<FeedCommunityViewModel>(activity)
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
                    liked = state.likedPosts.contains("${post.verseId}_${arg.communityId}"),
                    onLikePost = {
                        sharedViewModel.likeAPost(post = it, communityId = arg.communityId)
                    },
                    onRemoveLikePost = {
                        sharedViewModel.likeAPost(
                            post = it,
                            communityId = arg.communityId,
                            isRemoving = true
                        )
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
            appViewModel.videoPlayerVisible = true
            viewModel.prepareVideo(video)
        }

        state.selectedPost?.let {
            playerState.player?.let { player ->
                VideoPlayer(
                    post = it,
                    player = player,
                    onPlay = viewModel::playVideo,
                    onPause = viewModel::pauseVideo,
                    onSeekBack = viewModel::handleReplay,
                    onSeekForward = viewModel::handleReplay,
                    buffering = playerState.buffering,
                    isPlaying = playerState.isPlaying,
                    onReleasePlayer = {
                        viewModel.releasePlayer()
                        appViewModel.videoPlayerVisible = false
                        navController.popBackStack()
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
fun NavController.navigateToPlayer(videoUrl: String) =
    this.navigateBetweenScreens(Screen.VideoPlayer(videoUrl = videoUrl))

fun NavController.navigateToStories(email: String) =
    this.navigateBetweenScreens(Screen.StoriesNavigation(email))

//fun NavController.navigateToChatScreen(community: CommunityType) = this.navigateBetweenScreens(Screen.Chat(communityId = community.id))

fun NavController.navigateToCommunityDetails() =
    this.navigateBetweenScreens(Screen.CommunityDetails)

fun NavController.navigateToAddCommentOnPost(postId: String, communityId: String) =
    this.navigateBetweenScreens(Screen.CommentPost(postId, communityId))