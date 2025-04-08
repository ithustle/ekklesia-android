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
    isLoginActive: Boolean,
    navController: NavHostController,
    showDevocionalModal: (@Composable () -> Unit) -> Unit,
    hideDevocionalModal: () -> Unit
) {
    println("isLoginActive: $isLoginActive")
    NavHost(
        navController,
        startDestination = if (isLoginActive) Screen.HomeScreenGraph else Screen.AuthScreenGraph,
        modifier = modifier
    ) {
        authGraph(navController = navController)
        homeGraph(navController = navController)
        bibleGraph(
            navController = navController,
            showDevocionalModal = showDevocionalModal,
            hideDevocionalModal = hideDevocionalModal
        )
        communityGraph(navController = navController)
    }
}

fun NavController.navigateBetweenTabs(destination: Screen) = this.navigate(destination) {
    popUpTo(this@navigateBetweenTabs.graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}

fun NavController.navigateToFirstScreen(route: Screen) =
    this.navigate(route) {
        popUpTo(this@navigateToFirstScreen.graph.id)
    }

fun NavController.navigateBetweenScreens(route: Screen) =
    this.navigate(route) {
        launchSingleTop = true
        popUpTo(route) { inclusive = false }
    }