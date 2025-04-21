package com.toquemedia.ekklesia.ui.screens.community.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.CommunityWithMembers
import com.toquemedia.ekklesia.ui.composables.EkklesiaDialog
import com.toquemedia.ekklesia.ui.theme.backgroundLightColor
import com.toquemedia.ekklesia.utils.mocks.CommunityMock

@Composable
fun CommunityListScreen(
    modifier: Modifier = Modifier,
    onOpenToCreateCommunity: () -> Unit = {},
    onNavigateToCommunity: (CommunityWithMembers) -> Unit = {},
    onDismissDialog: () -> Unit = {},
    onDeleteCommunity: (String) -> Unit = {},
    onOpenDialogChange: (Boolean) -> Unit = {},
    myCommunities: List<CommunityWithMembers>,
    openDialog: Boolean
) {

    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }
    var communityIdSelected by remember { mutableStateOf<String?>(null) }

    if (openDialog) {
        EkklesiaDialog(
            onDismissRequest = {
                onOpenDialogChange(false)
                onDismissDialog()
           },
            onConfirmation = {
                onOpenDialogChange(false)
                onDeleteCommunity(communityIdSelected.toString())
            },
            dialogTitle = stringResource(R.string.dialog_community_title),
            dialogText = stringResource(R.string.dialog_community_text)
        )
    }

    Box {
        Column(
            modifier = modifier
                .background(color = backgroundLightColor)
                .padding(horizontal = 16.dp, vertical = 30.dp)
                .fillMaxSize()
        ) {
            CommunityButtonAdd(
                onTapAction = onOpenToCreateCommunity
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn {
                items(myCommunities) { data ->
                    val expanded = expandedStates[data.community.id] == true
                    CommunityCard(
                        community = data,
                        onNavigateToCommunity = {
                            onNavigateToCommunity(data)
                        },
                        onOpenMoreMenu = { expandedStates[data.community.id.toString()] = !expanded },
                        modifier = modifier
                            .padding(bottom = 10.dp)
                    ) {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expandedStates[data.community.id.toString()] = false }
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = stringResource(R.string.delete_community)
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = stringResource(R.string.description_delete_community)
                                    )
                                },
                                onClick = {
                                    onOpenDialogChange(true)
                                    communityIdSelected = data.community.id
                                    expandedStates[data.community.id.toString()] = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommunityListScreenPrev() {
    CommunityListScreen(
        myCommunities = CommunityMock.getAllCommunityWithMembers(),
        openDialog = false,
    )
}