package com.toquemedia.ekklesia.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.model.BottomBarItem
import com.toquemedia.ekklesia.ui.screens.bible.TestamentScreen

fun NavGraphBuilder.bibleNavigation(navController: NavController) {
    composable(BottomBarItem.Bible.route) {

        val viewModel = LocalAppViewModel.current

        TestamentScreen(
            books = viewModel.books,
            onNavigateToChapter = {
                navController.navigateToChapter(bookName = it)
            },
            currentUser = viewModel.currentUser
        )
    }
}