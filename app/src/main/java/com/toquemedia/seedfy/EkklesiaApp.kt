package com.toquemedia.seedfy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import com.toquemedia.seedfy.model.EkklesiaBottomNavigation
import com.toquemedia.seedfy.model.TopBarState
import com.toquemedia.seedfy.model.UserType
import com.toquemedia.seedfy.routes.Screen
import com.toquemedia.seedfy.ui.composables.EkklesiaTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EkklesiaApp(
    modifier: Modifier = Modifier,
    tabSelected: Screen = Screen.Home,
    showOverlay: Boolean = false,
    onTabItemChange: (Screen) -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    currentUser: UserType?,
    topBarState: TopBarState,
    showTopBar: Boolean = true,
    videoPlayerVisible: Boolean = false,
    content: @Composable () -> Unit = {}
) {
    if (showOverlay) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.7f))
                .zIndex(1f)
        )
    }

    Scaffold(
        topBar = {
            if (currentUser != null && showTopBar) {
                EkklesiaTopBar(
                    title = topBarState.title,
                    isBackgroundTransparent = topBarState.isBackgroundTransparent,
                    navigationBack = topBarState.showBackButton,
                    showTitleAvatar = topBarState.showTitleAvatar,
                    userAvatar = topBarState.useAvatar,
                    onNavigateBack = onNavigateBack,
                    onNavigateToProfile = onNavigateToProfile,
                    actions = topBarState.actions
                )
            }
        },
        bottomBar = {
            if (currentUser != null) {
                when {
                    tabSelected == Screen.Home ||
                            tabSelected == Screen.Bible ||
                            tabSelected == Screen.Communities ||
                            tabSelected == Screen.Chapters(bookName = "") -> EkklesiaBottomNavigation(
                        currentScreen = tabSelected,
                        onTabSelected = onTabItemChange,
                    )

                    else -> return@Scaffold
                }
            }
        },
        modifier = if (videoPlayerVisible) Modifier else Modifier.windowInsetsPadding(WindowInsets.navigationBars)
    ) { padding ->
        Box(
            modifier = modifier
                .padding(padding)
        ) {
            content()
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
private fun EkklesiaAppPrev() {
    val currentUser = UserType(
        id = "1234",
        displayName = "Célio Garcia",
        photo = ""
    )
    EkklesiaApp(
        currentUser = currentUser,
        showTopBar = true,
        topBarState = TopBarState(title = "Aplicação Ekklesia")
    )
}