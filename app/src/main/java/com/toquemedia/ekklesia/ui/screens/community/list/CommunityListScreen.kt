package com.toquemedia.ekklesia.ui.screens.community.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.composables.HeadTitle
import com.toquemedia.ekklesia.ui.screens.community.CommunityUiState
import com.toquemedia.ekklesia.ui.screens.community.composables.CommunityButtonAdd
import com.toquemedia.ekklesia.ui.screens.community.composables.CommunityCard
import com.toquemedia.ekklesia.ui.theme.backgroundLightColor

@Composable
fun CommunityListScreen(
    modifier: Modifier = Modifier,
    onOpenToCreateCommunity: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    state: CommunityUiState
) {
    Column(
        modifier = modifier
            .background(color = backgroundLightColor)
            .padding(horizontal = 16.dp, vertical = 30.dp)
            .fillMaxSize()
    ) {
        HeadTitle(
            photo = state.userPhoto,
            title = stringResource(R.string.community),
            onNavigateToProfile = onNavigateToProfile
        )

        Spacer(modifier = Modifier.height(24.dp))

        CommunityButtonAdd(
            onTapAction = onOpenToCreateCommunity
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            items(state.communities) { community ->
                CommunityCard(
                    community = community,
                    onTapAction = {},
                    modifier = modifier
                        .padding(bottom = 10.dp)
                )
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