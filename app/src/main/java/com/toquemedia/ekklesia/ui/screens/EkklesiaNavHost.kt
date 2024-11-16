package com.toquemedia.ekklesia.ui.screens

import com.toquemedia.ekklesia.model.BottomBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.toquemedia.ekklesia.ui.navigation.bibleNavigation
import com.toquemedia.ekklesia.ui.navigation.chapterNavigation
import com.toquemedia.ekklesia.ui.navigation.communityNavigation
import com.toquemedia.ekklesia.ui.navigation.homeNavigation
import com.toquemedia.ekklesia.ui.navigation.verseNavigation

@Composable
fun EkklesiaNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    showDevocionalModal: (@Composable () -> Unit) -> Unit,
    hideDevocionalModal: () -> Unit
) {
    NavHost(
        navController,
        startDestination = BottomBarItem.Home.route,
        modifier = modifier
    ) {
        homeNavigation()
        bibleNavigation(navController = navController)
        communityNavigation(navController = navController)
        chapterNavigation(navController = navController)
        verseNavigation(
            showDevocionalModal = showDevocionalModal,
            hideDevocionalModal = hideDevocionalModal
        )
    }
}

fun NavHostController.navigateBetweenTabs(destination: String) = this.navigate(destination) {
    popUpTo(this@navigateBetweenTabs.graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}