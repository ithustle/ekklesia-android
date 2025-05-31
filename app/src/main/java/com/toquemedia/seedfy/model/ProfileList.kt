package com.toquemedia.seedfy.model

import androidx.compose.ui.graphics.vector.ImageVector

sealed class ProfileList(val title: String, val icon: ImageVector, val onAction: () -> Unit) {
    class Biography(title: String, icon: ImageVector, onAction: () -> Unit) : ProfileList(title, icon, onAction)
    class Devocional(title: String, icon: ImageVector, onAction: () -> Unit) : ProfileList(title, icon, onAction)
    class BiblePlan(title: String, icon: ImageVector, onAction: () -> Unit) : ProfileList(title, icon, onAction)
    class Logout(title: String, icon: ImageVector, onAction: () -> Unit) : ProfileList(title, icon, onAction)
}