package com.toquemedia.seedfy.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.toquemedia.seedfy.LocalAppViewModel
import com.toquemedia.seedfy.model.TopBarState
import com.toquemedia.seedfy.routes.Screen
import com.toquemedia.seedfy.routes.navigateBetweenScreens
import com.toquemedia.seedfy.ui.screens.bible.ProposalTestamentScreen
import com.toquemedia.seedfy.ui.screens.bible.TestamentViewModel
import com.toquemedia.seedfy.ui.screens.bible.search.SearchAIScreen

fun NavGraphBuilder.bibleNavigation(navController: NavController) {
    composable<Screen.Bible> {
        val viewModel = hiltViewModel<TestamentViewModel>(it)

        val appViewModel = LocalAppViewModel.current
        val currentUser = appViewModel.currentUser.value

        LaunchedEffect(Unit) {
            appViewModel.updateTopBarState(
                newState = TopBarState(
                    title = "Testamentos",
                    useAvatar = currentUser?.photo
                )
            )
        }

        ProposalTestamentScreen(
            books = appViewModel.books,
            onNavigateToChapter = {
                navController.navigateToChapter(bookName = it)
            },
            onPerformSearch = {
                navController.navigateToSearchAi(userPrompt = it)
            }
        )
    }

    composable<Screen.SearchAi> {

        navController.previousBackStackEntry?.let { navEntry ->
            val viewModel = hiltViewModel<TestamentViewModel>(navEntry)
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            val args = it.toRoute<Screen.SearchAi>()
            val appViewModel = LocalAppViewModel.current
            val currentUser = appViewModel.currentUser.value

            LaunchedEffect(args.userPrompt) {
                viewModel.generateResponse(args.userPrompt)
            }

            LaunchedEffect(Unit) {
                appViewModel.updateTopBarState(
                    newState = TopBarState(
                        title = "Pesquisa",
                        useAvatar = currentUser?.photo,
                        showBackButton = true,
                        onBackNavigation = { navController.popBackStack() }
                    )
                )
            }

            state.searchResponse?.let {
                SearchAIScreen(
                    response = it,
                    onVerseClick = {},
                    onSaveStudyPlan = {

                    }
                )
            } ?: run {
                Text("A gerar ... ")
            }
        }
    }
}

fun NavController.navigateToSearchAi(userPrompt: String) = this.navigateBetweenScreens(Screen.SearchAi(userPrompt = userPrompt))