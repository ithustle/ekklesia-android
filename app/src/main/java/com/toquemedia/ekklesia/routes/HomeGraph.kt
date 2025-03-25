package com.toquemedia.ekklesia.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.toquemedia.ekklesia.model.BottomBarItem
import com.toquemedia.ekklesia.ui.navigation.homeNavigation
import com.toquemedia.ekklesia.ui.navigation.profileNavigation

internal const val homeGraphRoute = "homeGraph"

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    navigation(startDestination = BottomBarItem.Home.route, route = homeGraphRoute) {
        homeNavigation()
        profileNavigation(navController = navController)
    }
}

fun NavController.navigateToHomeGraph() = this.navigateBetweenTabs(homeGraphRoute)