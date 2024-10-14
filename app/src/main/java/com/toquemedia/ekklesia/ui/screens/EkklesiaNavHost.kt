package com.toquemedia.ekklesia.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.ui.screens.bible.TestamentScreen
import com.toquemedia.ekklesia.ui.screens.home.HomeScreen

@Composable
fun EkklesiaNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        navController,
        startDestination = BottomBarItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomBarItem.Home.route) {
            HomeScreen()
        }
        composable(BottomBarItem.Bible.route) {
            TestamentScreen()
        }
    }
}