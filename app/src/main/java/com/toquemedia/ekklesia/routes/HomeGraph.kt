package com.toquemedia.ekklesia.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.toquemedia.ekklesia.model.BottomBarItem
import com.toquemedia.ekklesia.ui.navigation.communityNavigation
import com.toquemedia.ekklesia.ui.navigation.profileNavigation

internal const val homeGraphRoute = "homeGraph"

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    navigation(startDestination = BottomBarItem.Community.route, route = homeGraphRoute) {
        //homeNavigation()
        communityNavigation(navController = navController)
        profileNavigation(navController = navController)
    }
}

fun NavController.navigateToHomeGraph() =
    this.navigateToFirstScreen(homeGraphRoute)