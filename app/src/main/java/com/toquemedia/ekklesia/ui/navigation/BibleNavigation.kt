package com.toquemedia.ekklesia.ui.navigation

import com.toquemedia.ekklesia.model.BottomBarItem
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.ui.screens.bible.TestamentScreen
import com.toquemedia.ekklesia.ui.screens.bible.TestamentViewModel
import com.toquemedia.ekklesia.ui.screens.navigateBetweenTabs

fun NavGraphBuilder.bibleNavigation(navController: NavHostController) {
    composable(BottomBarItem.Bible.route) {
        val viewModel: TestamentViewModel = hiltViewModel()
        val states by viewModel.uiState.collectAsState()

        TestamentScreen(
            states = states,
            onNavigateToChapter = {
                navController.navigateToChapter(bookName = it)
            }
        )
    }
}

fun NavHostController.navigateToBible() {
    this.navigateBetweenTabs(BottomBarItem.Bible.route)
}