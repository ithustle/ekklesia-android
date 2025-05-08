package com.toquemedia.seedfy.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.model.CommunityWithMembers
import com.toquemedia.seedfy.model.WorshipEntity
import com.toquemedia.seedfy.ui.composables.EkklesiaDialog
import com.toquemedia.seedfy.ui.composables.EkklesiaLabelWithIcon
import com.toquemedia.seedfy.utils.mocks.CommunityMock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyWorshipScreen(
    worships: List<WorshipEntity>,
    communities: List<CommunityWithMembers>,
    sharingWorship: Boolean = false,
    onShareToCommunityWorship: (String, WorshipEntity) -> Unit = { _, _ -> },
    onEditWorship: (WorshipEntity) -> Unit = {},
    onDeleteWorship: (WorshipEntity) -> Unit = {},
    onDialogOpened: () -> Unit = {},
    onDismissDialog: () -> Unit = {},
) {

    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }
    var openDialog by remember { mutableStateOf(false) }
    var selectedWorship by remember { mutableStateOf<WorshipEntity?>(null) }

    LaunchedEffect(sharingWorship) {
        if (sharingWorship == false) {
            openDialog = false
            onDismissDialog()
        }
    }

    if (openDialog) {
        EkklesiaDialog(
            onDismissRequest = {
                openDialog = it
                onDismissDialog()
            }
        ) {
            MyCommunities(
                modifier = Modifier.fillMaxWidth(),
                sharingWorship = sharingWorship,
                communities = communities,
                onShareToCommunity = {
                    selectedWorship?.let { worship ->
                        onShareToCommunityWorship(it.community.id, worship)
                    }
                }
            )
        }
    }

    if (worships.isEmpty()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            WorshipInstructions()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Spacer(Modifier.height(16.dp))
        }

        items(worships) { worship ->

            val expanded = expandedStates[worship.id] == true

            WorshipCard(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                worship = worship,
                onLongPress = { expandedStates[worship.id] = !expanded },
                onShareWorship = {
                    selectedWorship = worship
                    openDialog = true
                    onDialogOpened()
                }
            ) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expandedStates[worship.id] = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            EkklesiaLabelWithIcon(
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = null
                                    )
                                },
                                label = stringResource(R.string.menu_edit)
                            )
                        },
                        onClick = {
                            expandedStates[worship.id] = false
                            onEditWorship(worship)
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            EkklesiaLabelWithIcon(
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null
                                    )
                                },
                                label = stringResource(R.string.delete_community)
                            )
                        },
                        onClick = {
                            expandedStates[worship.id] = false
                            onDeleteWorship(worship)
                        }
                    )
                }
            }
        }

        item {
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyWorshipScreenPrev() {
    MyWorshipScreen(
        worships = emptyList(),
        communities = CommunityMock.getAllCommunityWithMembers()
    )
}