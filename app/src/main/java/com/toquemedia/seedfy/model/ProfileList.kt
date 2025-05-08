package com.toquemedia.seedfy.model

import androidx.compose.ui.graphics.vector.ImageVector

sealed class ProfileList(val title: String, val icon: ImageVector, val onAction: () -> Unit) {
    class Devocional(title: String, icon: ImageVector, onAction: () -> Unit) : ProfileList(title, icon, onAction)
    class Logout(title: String, icon: ImageVector, onAction: () -> Unit) : ProfileList(title, icon, onAction)
}