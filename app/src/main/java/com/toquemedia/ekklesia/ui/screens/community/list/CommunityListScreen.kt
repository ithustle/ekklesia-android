package com.toquemedia.ekklesia.ui.screens.community.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.CommunityEntity
import com.toquemedia.ekklesia.ui.composables.EkklesiaTopBar
import com.toquemedia.ekklesia.ui.composables.HeadTitle
import com.toquemedia.ekklesia.ui.screens.community.CommunityUiState
import com.toquemedia.ekklesia.ui.theme.backgroundLightColor

@Composable
fun CommunityListScreen(
    modifier: Modifier = Modifier,
    onOpenToCreateCommunity: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToCommunity: (CommunityEntity) -> Unit = {},
    state: CommunityUiState
) {

    val userAvatar = LocalAppViewModel.current.currentUser?.photo

    Scaffold(
        topBar = {
            EkklesiaTopBar(
                title = stringResource(R.string.community),
                onNavigateToProfile = onNavigateToProfile,
                isBackgroundTransparent = true,
                navigationBack = false,
                userAvatar = userAvatar
            )
        }
    ) {
        Column(
            modifier = modifier
                .background(color = backgroundLightColor)
                .padding(it)
                .padding(horizontal = 16.dp, vertical = 30.dp)
                .fillMaxSize()
        ) {

            CommunityButtonAdd(
                onTapAction = onOpenToCreateCommunity
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn {
                items(state.communities) { community ->
                    CommunityCard(
                        community = community,
                        onNavigateToCommunity = {
                            onNavigateToCommunity(community)
                        },
                        modifier = modifier
                            .padding(bottom = 10.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CommunityListScreenPrev() {
    CommunityListScreen(
        state = CommunityUiState()
    )
}