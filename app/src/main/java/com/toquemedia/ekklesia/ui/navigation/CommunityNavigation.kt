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
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.extension.toCommunity
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

        val appViewModel = LocalAppViewModel.current
        val viewModel = hiltViewModel<CommunityViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        val context = LocalContext.current
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

        val launcherCommunityPhoto =
            rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
                it?.let {
                    uiState.onImageUriChange(it.toString())
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

        val context = LocalContext.current
        val appViewModel = LocalAppViewModel.current
        val user = appViewModel.currentUser

        val viewModel = hiltViewModel<FeedCommunityViewModel>()
        val state by viewModel.uiState.collectAsState()
        val args = stackEntry.toRoute<Screen.CommunityFeed>()

        val community = sharedViewModel.getCurrentCommunity(args.communityId)

        LaunchedEffect(args.communityId) {
            viewModel.getAllPosts(args.communityId)
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
                                    Toast.makeText(context, "Funcionalidade em desenvolvimento", Toast.LENGTH_SHORT).show()
                                }
                        )
                    },
                    onBackNavigation = {
                        navController.popBackStack()
                    }
                )
            )
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
                },
                onAddStory = {
                    Toast.makeText(context, "Funcionalidade em desenvolvimento", Toast.LENGTH_SHORT).show()
                    //navController.navigateToChatScreen(community = community)
                }
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