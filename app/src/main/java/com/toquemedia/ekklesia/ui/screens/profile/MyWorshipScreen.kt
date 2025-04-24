package com.toquemedia.ekklesia.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.WorshipEntity
import com.toquemedia.ekklesia.ui.composables.EkklesiaLabelWithIcon
import com.toquemedia.ekklesia.utils.mocks.WorshipMock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyWorshipScreen(
    worships: List<WorshipEntity>,
    onShareWorship: (WorshipEntity) -> Unit = {},
    onEditWorship: (WorshipEntity) -> Unit = {},
    onDeleteWorship: (WorshipEntity) -> Unit = {}
) {

    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

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
                    onShareWorship(worship)
                }
            ) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expandedStates[worship.id] = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            EkklesiaLabelWithIcon(
                                icon = { Icon(imageVector = Icons.Default.Edit, contentDescription = null) },
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
                                icon = { Icon(imageVector = Icons.Default.Delete, contentDescription = null) },
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
        worships = WorshipMock.getAll()
    )
}