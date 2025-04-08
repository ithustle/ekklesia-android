package com.toquemedia.ekklesia.ui.screens.home

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.extension.toCommunity
import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.model.VerseType
import com.toquemedia.ekklesia.ui.theme.PrincipalColor
import com.toquemedia.ekklesia.utils.mocks.CommunityMock

@Composable
fun HomeScreen(
    context: Context,
    state: HomeUiState = HomeUiState(),
    onJoinToCommunity: (String) -> Unit = {},
    onNavigateToCommunity: (CommunityType) -> Unit = {}
) {

    val communities = state.communities

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {

        item {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(16.dp))
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
                        verse = state.verseOfDay
                    )
                }
            }
        }

        item {
            Spacer(Modifier.height(38.dp))
        }

        if (state.loadingCommunitiesUserIn) {
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp),
                        strokeWidth = 2.dp
                    )
                }
            }
        } else if (state.communitiesUserIn.isNotEmpty()) {

            item {
                Text(
                    text = stringResource(R.string.community),
                    fontSize = 20.sp,
                    color = PrincipalColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(bottom = 6.dp)
                        .padding(horizontal = 16.dp)
                )
            }

            item {
                LazyRow {
                    items(items = state.communitiesUserIn) {
                        Spacer(Modifier.width(16.dp))
                        Row {
                            it.community?.toCommunity(context)?.let {
                                CommunityInside(
                                    community = it,
                                    onNavigateToCommunity = onNavigateToCommunity
                                )
                            }
                        }
                    }
                }
            }
        }

        if (state.loadCommunities) {
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp),
                        strokeWidth = 2.dp
                    )
                }
            }
        } else if (state.communities.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.other_communities),
                    fontSize = 20.sp,
                    color = PrincipalColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(bottom = 6.dp, top = 16.dp)
                        .padding(horizontal = 16.dp)
                )
            }

            items(count = communities.size) {index ->
                HomeCommunity(
                    community = communities[index].community!!.toCommunity(context),
                    members = communities[index].allMembers!!,
                    loading = state.joiningToCommunity,
                    onJoinToCommunity = onJoinToCommunity,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePrev() {
    HomeScreen(
        state = HomeUiState(
            verseOfDay = VerseType(
                bookName = "Jó",
                chapter = 42,
                versicle = 2,
                text = "Bem sei que tudo podes, e que nada te impede de ser feito."
            ),
            communities = CommunityMock.getAllCommunityWithMembers(),
            communitiesUserIn = CommunityMock.getAllCommunityWithMembers()
        ),
        context = LocalContext.current,
    )
}