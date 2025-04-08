package com.toquemedia.ekklesia

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.outlined.SyncAlt
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toquemedia.ekklesia.model.EkklesiaBottomNavigation
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.ui.composables.EkklesiaModalSheet
import com.toquemedia.ekklesia.ui.composables.EkklesiaTopBar
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

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
    onNavigateBack: () -> Unit = {},
    currentUser: UserType?,
    content: @Composable () -> Unit = {}
) {

    val appViewModel = hiltViewModel<AppViewModel>()

    EkklesiaModalSheet(
        sheetState = sheetState,
        sheetContent = sheetContent,
    ) {
        Scaffold(
            topBar = {
                if (currentUser != null) {
                    when {
                        tabSelected == Screen.Home ||
                                tabSelected == Screen.Bible ||
                                tabSelected == Screen.Communities ||
                                     tabSelected == Screen.CreateCommunity ->
                            EkklesiaTopBar(
                                title = appViewModel.topBarTitle ?: "",
                                isBackgroundTransparent = true,
                                navigationBack = tabSelected == Screen.CreateCommunity,
                                onNavigateToProfile = onNavigateToProfile,
                                userAvatar = currentUser.photo,
                                onNavigateBack = onNavigateBack,
                                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
                            )
                        else -> EkklesiaTopBar(
                            title = appViewModel.topBarTitle ?: "",
                            isBackgroundTransparent = true,
                            navigationBack = true,
                            showTitleAvatar = true,
                            userAvatar = currentUser.photo,
                            onNavigateBack = onNavigateBack,
                            actions = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.Chat,
                                    contentDescription = stringResource(R.string.change_community_description),
                                    tint = PrincipalColor,
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp)
                                        .size(20.dp)
                                        .clickable {
                                            //onNavigateToChat()
                                        }
                                )
                                Icon(
                                    imageVector = Icons.Outlined.SyncAlt,
                                    contentDescription = stringResource(R.string.change_community_description),
                                    tint = PrincipalColor,
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp)
                                        .size(20.dp)
                                        .clickable {
                                            //onSelectCommunity()
                                        }
                                )
                            }
                        )
                    }
                }
            },
            bottomBar = {
                if (currentUser != null) {
                    when {
                        tabSelected == Screen.Home ||
                                tabSelected == Screen.Bible ||
                                tabSelected == Screen.Communities -> EkklesiaBottomNavigation(
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
        currentUser = currentUser
    )
}