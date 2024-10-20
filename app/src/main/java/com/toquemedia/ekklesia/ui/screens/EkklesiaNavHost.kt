package com.toquemedia.ekklesia.ui.screens

import BottomBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.toquemedia.ekklesia.ui.navigation.bibleNavigation
import com.toquemedia.ekklesia.ui.navigation.chapterNavigation
import com.toquemedia.ekklesia.ui.navigation.homeNavigation
import com.toquemedia.ekklesia.ui.navigation.verseNavigation

@Composable
fun EkklesiaNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        navController,
        startDestination = BottomBarItem.Home.route,
        modifier = modifier
    ) {
        homeNavigation()
        bibleNavigation(navController = navController)
        chapterNavigation(navController = navController)
        verseNavigation()
    }
}

fun NavHostController.navigateBetweenTabs(destination: String) = this.navigate(destination) {
    popUpTo(this@navigateBetweenTabs.graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}