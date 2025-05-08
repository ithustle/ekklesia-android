package com.toquemedia.seedfy.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.toquemedia.seedfy.ui.navigation.bibleNavigation
import com.toquemedia.seedfy.ui.navigation.chapterNavigation
import com.toquemedia.seedfy.ui.navigation.verseNavigation

fun NavGraphBuilder.bibleGraph(
    navController: NavController
) {
    navigation<Screen.BibleScreenGraph>(startDestination = Screen.Bible) {
        bibleNavigation(navController = navController)
        chapterNavigation(navController = navController)
        verseNavigation(navController = navController)
    }
}

fun NavController.navigateToBibleGraph() = this.navigateBetweenTabs(Screen.BibleScreenGraph)