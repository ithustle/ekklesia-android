package com.toquemedia.ekklesia

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.toquemedia.ekklesia.model.EkklesiaBottomNavigation
import com.toquemedia.ekklesia.model.TopBarState
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.ui.composables.EkklesiaTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EkklesiaApp(
    modifier: Modifier = Modifier,
    tabSelected: Screen = Screen.Home,
    onTabItemChange: (Screen) -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    currentUser: UserType?,
    topBarState: TopBarState,
    content: @Composable () -> Unit = {}
) {

    Scaffold(
        topBar = {
            if (currentUser != null) {
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
                        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                    )
                    else -> return@Scaffold
                }
            }
        },
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) { padding ->
        Box(
            modifier = modifier
                .padding(padding)
                .windowInsetsPadding(WindowInsets.navigationBars)
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
        topBarState = TopBarState(title = "Aplicação Ekklesia")
    )
}