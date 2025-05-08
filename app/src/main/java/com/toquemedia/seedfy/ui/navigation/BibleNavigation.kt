package com.toquemedia.seedfy.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.toquemedia.seedfy.LocalAppViewModel
import com.toquemedia.seedfy.model.TopBarState
import com.toquemedia.seedfy.routes.Screen
import com.toquemedia.seedfy.ui.screens.bible.TestamentScreen

fun NavGraphBuilder.bibleNavigation(navController: NavController) {
    composable<Screen.Bible> {

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

        TestamentScreen(
            books = appViewModel.books,
            onNavigateToChapter = {
                navController.navigateToChapter(bookName = it)
            }
        )
    }
}