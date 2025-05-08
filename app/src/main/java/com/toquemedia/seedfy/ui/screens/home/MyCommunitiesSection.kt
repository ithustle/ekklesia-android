package com.toquemedia.seedfy.ui.screens.home

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.model.CommunityWithMembers
import com.toquemedia.seedfy.ui.composables.LoadingItem
import com.toquemedia.seedfy.ui.composables.SectionTitle

@Composable
fun MyCommunitiesSection(
    context: Context,
    loadingCommunitiesUserIn: Boolean = true,
    communitiesUserIn: List<CommunityWithMembers> = emptyList(),
    onNavigateToCommunity: (CommunityWithMembers) -> Unit = {}
) {
    if (loadingCommunitiesUserIn) {
        LoadingItem()
    } else if (communitiesUserIn.isNotEmpty()) {
        Column {
            SectionTitle(stringResource(R.string.community))
            LazyRow {
                items(items = communitiesUserIn) {
                    Spacer(Modifier.width(16.dp))
                    Row {
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

@Preview
@Composable
private fun MyCommunitiesSectionPrev() {
    MyCommunitiesSection(context = LocalContext.current)
}