package com.toquemedia.ekklesia.ui.navigation

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.model.BottomBarItem
import com.toquemedia.ekklesia.ui.screens.login.LoginScreen

fun NavGraphBuilder.authNavigation(
    navController: NavHostController,
    onClickLogin: () -> Unit
) {
    composable(BottomBarItem.Login.route) {
        LoginScreen(
            onClickLogin = onClickLogin
        )
    }
}