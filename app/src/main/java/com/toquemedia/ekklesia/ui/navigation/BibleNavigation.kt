package com.toquemedia.ekklesia.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.model.TopBarState
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.ui.screens.bible.TestamentScreen

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