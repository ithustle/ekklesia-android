package com.toquemedia.ekklesia.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.toquemedia.ekklesia.ui.navigation.bibleNavigation
import com.toquemedia.ekklesia.ui.navigation.chapterNavigation
import com.toquemedia.ekklesia.ui.navigation.verseNavigation

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