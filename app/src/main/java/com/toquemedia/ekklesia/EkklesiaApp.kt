package com.toquemedia.ekklesia

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.toquemedia.ekklesia.model.EkklesiaBottomNavigation
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.ui.composables.EkklesiaModalSheet
import com.toquemedia.ekklesia.ui.composables.EkklesiaTopBar

@Composable
fun EkklesiaApp(
    modifier: Modifier = Modifier,
    sheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    ),
    sheetContent: @Composable () -> Unit = {},
    tabSelected: Screen = Screen.Home,
    onTabItemChange: (Screen) -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    currentUser: UserType?,
    content: @Composable () -> Unit = {},
    topBarTitle: String
) {
    EkklesiaModalSheet(
        sheetState = sheetState,
        sheetContent = sheetContent,
    ) {
        Scaffold(
            topBar = {
                if (currentUser != null) {
                    EkklesiaTopBar(
                        title = topBarTitle,
                        isBackgroundTransparent = true,
                        navigationBack = false,
                        onNavigateToProfile = onNavigateToProfile,
                        userAvatar = currentUser.photo,
                        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
                    )
                }
            },
            bottomBar = {
                if (currentUser != null) {
                    EkklesiaBottomNavigation(
                        currentScreen = tabSelected,
                        onTabSelected = onTabItemChange,
                        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                    )
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
        photo = "".toUri()
    )
    EkklesiaApp(
        currentUser = currentUser,
        topBarTitle = "Bom dia, ${currentUser.displayName}"
    )
}