package com.toquemedia.seedfy.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.toquemedia.seedfy.ui.navigation.homeNavigation
import com.toquemedia.seedfy.ui.navigation.profileNavigation


fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    navigation<Screen.HomeScreenGraph>(startDestination = Screen.Home) {
        homeNavigation(navController = navController)
        profileNavigation(navController = navController)
    }
}

fun NavController.navigateToHomeGraph() = this.navigateBetweenTabs(Screen.HomeScreenGraph)