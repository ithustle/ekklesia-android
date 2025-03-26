package com.toquemedia.ekklesia.ui.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.extension.toCommunity
import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.model.createNavParametersScreenType
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.routes.navigateBetweenScreens
import com.toquemedia.ekklesia.ui.screens.community.CommunityViewModel
import com.toquemedia.ekklesia.ui.screens.community.chat.ChatScreen
import com.toquemedia.ekklesia.ui.screens.community.chat.ChatViewModel
import com.toquemedia.ekklesia.ui.screens.community.create.CreateCommunityScreen
import com.toquemedia.ekklesia.ui.screens.community.feed.FeedCommunityViewModel
import com.toquemedia.ekklesia.ui.screens.community.feed.FeedScreen
import com.toquemedia.ekklesia.ui.screens.community.list.CommunityListScreen
import kotlin.reflect.typeOf

fun NavGraphBuilder.communityNavigation(navController: NavController) {
    composable<Screen.Communities> {

        val viewModel = hiltViewModel<CommunityViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        val context = LocalContext.current

        CommunityListScreen(
            onOpenToCreateCommunity = {
                navController.navigateToCreateCommunity()
            },
            onNavigateToProfile = {
                navController.navigateToProfile()
            },
            onNavigateToCommunity = {
                var community = it.toCommunity(context)
                navController.navigateToCommunityFeed(community = community)
            },
            state = uiState
        )
    }

    composable<Screen.CreateCommunity> {

        val viewModel = hiltViewModel<CommunityViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        val user = LocalAppViewModel.current.currentUser

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
                viewModel.createCommunity(user?.email)
            },
            onCreateSuccessfulCommunity = {
                navController.popBackStack()
            }
        )
    }

    composable<Screen.CommunityFeed>(
        typeMap = mapOf(typeOf<CommunityType>() to createNavParametersScreenType<CommunityType>())
    ) { stackEntry ->

        val viewModel = hiltViewModel<FeedCommunityViewModel>()
        val state by viewModel.uiState.collectAsState()
        val args = stackEntry.toRoute<Screen.CommunityFeed>()

        FeedScreen(
            community = args.community,
            state = state,
            onNavigateToChat = {
                navController.navigateToChatScreen(community = args.community)
            }
        )
    }

    composable<Screen.Chat>(
        typeMap = mapOf(typeOf<CommunityType>() to createNavParametersScreenType<CommunityType>())
    ) { stackEntry ->

        val viewModel = hiltViewModel<ChatViewModel>()
        val state by viewModel.uiState.collectAsState()
        val arg = stackEntry.toRoute<Screen.Chat>()

        ChatScreen(
            community = arg.community,
            state = state
        )
    }
}

fun NavController.navigateToCreateCommunity() = this.navigateBetweenScreens(Screen.CreateCommunity)
fun NavController.navigateToCommunityFeed(community: CommunityType) = this.navigateBetweenScreens(Screen.CommunityFeed(community))
fun NavController.navigateToChatScreen(community: CommunityType) = this.navigateBetweenScreens(Screen.Chat(community))