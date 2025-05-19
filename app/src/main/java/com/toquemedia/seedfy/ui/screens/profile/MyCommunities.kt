package com.toquemedia.seedfy.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.model.CommunityWithMembers
import com.toquemedia.seedfy.ui.composables.EkklesiaProgress
import com.toquemedia.seedfy.ui.screens.community.list.CommunityCard
import com.toquemedia.seedfy.ui.theme.PrincipalColor
import com.toquemedia.seedfy.utils.mocks.CommunityMock

@Composable
fun MyCommunities(
    modifier: Modifier = Modifier,
    sharing: Boolean = false,
    communities: List<CommunityWithMembers>,
    onShareToCommunity: (CommunityWithMembers) -> Unit = {}
) {

    var selectedCommunity by remember { mutableStateOf<CommunityWithMembers?>(null) }

    Column {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.communities),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrincipalColor,
                modifier = Modifier.padding(start = 16.dp)
            )

            selectedCommunity?.let {

                if (sharing) {
                    IconButton(onClick = {}) {
                        EkklesiaProgress(
                            modifier = Modifier.padding(top = 4.dp),
                            color = PrincipalColor,
                            size = 28.dp
                        )
                    }
                } else {
                    IconButton(
                        onClick = {
                            onShareToCommunity(it)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = stringResource(R.string.share_description),
                            tint = PrincipalColor
                        )
                    }
                }
            } ?: run {
                IconButton(onClick = {}) {}
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            items(communities) {
                CommunityCard(
                    modifier = Modifier,
                    community = it,
                    selectedCommunity = selectedCommunity?.community?.id,
                    onSelectedCommunity = {
                        selectedCommunity = it
                    }
                )
                Spacer(Modifier.height(12.dp))
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun MyCommunitiesPrev() {
    MyCommunities(
        modifier = Modifier
            .fillMaxWidth(),
        communities = CommunityMock.getAllCommunityWithMembers()
    )
}