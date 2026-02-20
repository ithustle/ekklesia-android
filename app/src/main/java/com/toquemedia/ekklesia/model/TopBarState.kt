package com.toquemedia.ekklesia.model

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class TopBarState(
    val title: String,
    val showBackButton: Boolean = false,
    val showTitleAvatar: Boolean = false,
    val useAvatar: String? = null,
    val isBackgroundTransparent: Boolean = true,
    val actions: @Composable (RowScope.() -> Unit) = {},
    val onBackNavigation: () -> Unit = {}
)
