package com.toquemedia.ekklesia.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.toquemedia.ekklesia.model.BottomBarItem
import com.toquemedia.ekklesia.ui.navigation.bibleNavigation
import com.toquemedia.ekklesia.ui.navigation.chapterNavigation
import com.toquemedia.ekklesia.ui.navigation.verseNavigation

internal const val bibleGraphRoute = "bibleGraph"

fun NavGraphBuilder.bibleGraph(
    navController: NavController,
    showDevocionalModal: (@Composable () -> Unit) -> Unit = {},
    hideDevocionalModal: () -> Unit = {}
) {
    navigation(startDestination = BottomBarItem.Bible.route, route = bibleGraphRoute) {
        bibleNavigation(navController = navController)
        chapterNavigation(navController = navController)
        verseNavigation(
            showDevocionalModal = showDevocionalModal,
            hideDevocionalModal = hideDevocionalModal
        )
    }
}

fun NavController.navigateToBibleGraph() = this.navigateBetweenTabs(bibleGraphRoute)