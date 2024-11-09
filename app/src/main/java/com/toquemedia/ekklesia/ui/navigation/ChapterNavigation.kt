package com.toquemedia.ekklesia.ui.navigation

import Screen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.ui.screens.bible.TestamentViewModel
import com.toquemedia.ekklesia.ui.screens.bible.chapter.ChapterScreen

fun NavGraphBuilder.chapterNavigation(navController: NavHostController) {
    composable("${Screen.Chapters.route}/{bookName}") { backStackEntry ->

        val viewModel: TestamentViewModel = hiltViewModel()
        val states by viewModel.uiState.collectAsState()
        val bookName = backStackEntry.arguments?.getString("bookName")

        bookName?.let {
            viewModel.getChaptersOfTheBook(it)
        }

        ChapterScreen(
            states = states,
            onNavigateToVerses = {
                navController.navigateToChapterVerse(bookName, chapterNumber = it.toString())
            }
        )
    }
}

fun NavHostController.navigateToChapter(bookName: String) {
    navigate("${Screen.Chapters.route}/$bookName") {
        launchSingleTop = true
        popUpTo(Screen.Chapters.route)
    }
}