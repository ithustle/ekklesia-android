package com.toquemedia.ekklesia.ui.navigation

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.BottomBarItem
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.ui.screens.bible.TestamentScreen

fun NavGraphBuilder.bibleNavigation(navController: NavController) {
    composable<Screen.Bible> {

        val appViewModel = LocalAppViewModel.current
        appViewModel.topBarTitle = stringResource(R.string.bible_all)

        TestamentScreen(
            books = appViewModel.books,
            onNavigateToChapter = {
                navController.navigateToChapter(bookName = it)
            }
        )
    }
}