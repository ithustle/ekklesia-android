package com.toquemedia.ekklesia.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.composables.EkklesiaTopBar
import com.toquemedia.ekklesia.ui.theme.PrincipalColor
import com.toquemedia.ekklesia.utils.mocks.CommunityMock

@Composable
fun HomeScreen(
    state: HomeUiState = HomeUiState()
) {

    val appViewModel = LocalAppViewModel.current
    val avatarUser = appViewModel.currentUser

    LaunchedEffect(state.verseOfDay) {
        appViewModel.getVerseOfDay(state.verseOfDay)
    }

    Scaffold(
        topBar = {
            EkklesiaTopBar(
                title = "Bom dia, ${avatarUser?.displayName}",
                isBackgroundTransparent = true,
                navigationBack = false,
                userAvatar = avatarUser?.photo
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)

        ) {

            if (state.verseOfDay == null) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(214.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp),
                        strokeWidth = 2.dp
                    )
                }
            } else {
                VerseOfDay(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp)),
                    verse = appViewModel.verseOfTheDay
                )
            }

            Spacer(Modifier.height(38.dp))

            Column {
                Text(
                    text = stringResource(R.string.community),
                    fontSize = 24.sp,
                    color = PrincipalColor,
                    fontWeight = FontWeight.SemiBold
                )
            }

            LazyColumn(
                userScrollEnabled = false
            ) {
                items(count = CommunityMock.getAll().size) {
                    Spacer(Modifier.height(16.dp))
                    HomeCommunity(
                        community = CommunityMock.getAll()[it],
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePrev() {
    HomeScreen()
}