package com.toquemedia.ekklesia.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.routes.navigateBetweenScreens
import com.toquemedia.ekklesia.ui.screens.bible.TestamentViewModel
import com.toquemedia.ekklesia.ui.screens.bible.chapter.ChapterScreen

fun NavGraphBuilder.chapterNavigation(navController: NavController) {
    composable<Screen.Chapters> { backStackEntry ->

        val viewModel: TestamentViewModel = hiltViewModel()
        val states by viewModel.uiState.collectAsState()
        val arg = backStackEntry.toRoute<Screen.Chapters>()

        viewModel.getChaptersOfTheBook(arg.bookName)

        ChapterScreen(
            states = states,
            onNavigateToVerses = {
                navController.navigateToChapterVerse(arg.bookName, chapterNumber = it.toString())
            }
        )
    }
}

fun NavController.navigateToChapter(bookName: String) = this.navigateBetweenScreens(Screen.Chapters(bookName = bookName))