package com.toquemedia.seedfy.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.toquemedia.seedfy.ui.navigation.communityNavigation

fun NavGraphBuilder.communityGraph(navController: NavController) {
    navigation<Screen.CommunityScreenGraph>(startDestination = Screen.Communities) {
        communityNavigation(navController = navController)
    }
}

fun NavController.navigateToCommunityGraph() = this.navigateBetweenTabs(Screen.CommunityScreenGraph)