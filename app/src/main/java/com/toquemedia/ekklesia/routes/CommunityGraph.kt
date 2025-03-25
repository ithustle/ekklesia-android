package com.toquemedia.ekklesia.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.toquemedia.ekklesia.model.BottomBarItem
import com.toquemedia.ekklesia.ui.navigation.communityNavigation

internal const val communityGraphRoute = "communityGraph"

fun NavGraphBuilder.communityGraph(navController: NavController) {
    navigation(startDestination = BottomBarItem.Community.route, route = communityGraphRoute) {
        communityNavigation(navController = navController)
    }
}

fun NavController.navigateToCommunityGraph() = this.navigateBetweenTabs(communityGraphRoute)