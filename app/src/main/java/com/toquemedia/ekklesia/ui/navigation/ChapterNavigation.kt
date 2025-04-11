package com.toquemedia.ekklesia.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.extension.getGreeting
import com.toquemedia.ekklesia.model.TopBarState
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.routes.navigateBetweenScreens
import com.toquemedia.ekklesia.ui.composables.ScreenAppLoading
import com.toquemedia.ekklesia.ui.screens.bible.TestamentViewModel
import com.toquemedia.ekklesia.ui.screens.bible.chapter.ChapterScreen
import java.util.Date

fun NavGraphBuilder.chapterNavigation(navController: NavController) {
    composable<Screen.Chapters> { backStackEntry ->

        val appViewModel = LocalAppViewModel.current
        val currentUser = appViewModel.currentUser

        val viewModel: TestamentViewModel = hiltViewModel()
        val states by viewModel.uiState.collectAsState()
        val arg = backStackEntry.toRoute<Screen.Chapters>()

        LaunchedEffect(Unit) {
            viewModel.getChaptersOfTheBook(arg.bookName)
            appViewModel.updateTopBarState(
                newState = TopBarState(
                    title = arg.bookName,
                    showBackButton = true,
                    useAvatar = currentUser?.photo,
                    onBackNavigation = {
                        navController.popBackStack()
                    }
                )
            )
        }

        states.chapters?.let {
            ChapterScreen(
                chapters = it,
                onNavigateToVerses = {
                    navController.navigateToChapterVerse(arg.bookName, chapterNumber = it.toString())
                }
            )
        } ?: run {
            ScreenAppLoading(
                screenText = stringResource(R.string.loading_chapters)
            )
        }
    }
}

fun NavController.navigateToChapter(bookName: String) = this.navigateBetweenScreens(Screen.Chapters(bookName = bookName))