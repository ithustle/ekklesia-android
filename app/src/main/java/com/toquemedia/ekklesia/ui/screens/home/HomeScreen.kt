package com.toquemedia.ekklesia.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.composables.EkklesiaTopBar
import com.toquemedia.ekklesia.ui.theme.PrincipalColor
import com.toquemedia.ekklesia.utils.mocks.CommunityMock

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            EkklesiaTopBar(
                title = "Bom dia, Elsa",
                isBackgroundTransparent = true,
                navigationBack = false,
                showUserAvatar = true
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)

        ) {
            VerseOfDay(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
            )

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