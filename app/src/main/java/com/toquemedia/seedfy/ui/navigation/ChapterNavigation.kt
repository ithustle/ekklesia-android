package com.toquemedia.seedfy.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.toquemedia.seedfy.LocalAppViewModel
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.model.TopBarState
import com.toquemedia.seedfy.routes.Screen
import com.toquemedia.seedfy.routes.navigateBetweenScreens
import com.toquemedia.seedfy.ui.composables.ScreenAppLoading
import com.toquemedia.seedfy.ui.screens.bible.chapter.ChapterScreen

fun NavGraphBuilder.chapterNavigation(navController: NavController) {
    composable<Screen.Chapters> { backStackEntry ->

        val appViewModel = LocalAppViewModel.current
        val currentUser = appViewModel.currentUser.value

        val arg = backStackEntry.toRoute<Screen.Chapters>()

        LaunchedEffect(Unit) {
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

        val numberOfChapters = appViewModel.books.find { it.bookName == arg.bookName }?.numberOfChapters

        numberOfChapters?.let {
            ChapterScreen(
                chapters = it,
                onNavigateToVerses = {
                    navController.navigateToChapterVerse(arg.bookName, chapterNumber = it)
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