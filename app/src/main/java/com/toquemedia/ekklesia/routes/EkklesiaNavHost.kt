package com.toquemedia.ekklesia.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun EkklesiaNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    showDevocionalModal: (@Composable () -> Unit) -> Unit,
    hideDevocionalModal: () -> Unit
) {
    NavHost(
        navController,
        startDestination = bibleGraphRoute,
        modifier = modifier
    ) {
        authGraph(navController = navController)
        bibleGraph(
            navController = navController,
            showDevocionalModal = showDevocionalModal,
            hideDevocionalModal = hideDevocionalModal
        )
        homeGraph(navController = navController)
    }
}

fun NavController.navigateBetweenTabs(destination: String) = this.navigate(destination) {
    popUpTo(this@navigateBetweenTabs.graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}

fun NavController.navigateToFirstScreen(route: String) =
    this.navigate(route) {
        popUpTo(this@navigateToFirstScreen.graph.id)
    }