package com.toquemedia.seedfy.ui.composables

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.toquemedia.seedfy.ui.screens.profile.MyCommunities
import com.toquemedia.seedfy.utils.mocks.CommunityMock

@Composable
fun EkklesiaDialog(
    onDismissRequest: (Boolean) -> Unit = {},
    content: @Composable () -> Unit
) {

    BackHandler(enabled = true) {
        onDismissRequest(false)
    }

    Dialog(
        onDismissRequest = { onDismissRequest(false) },
        content = {
            Card(
                modifier = Modifier
                    .height(256.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                content()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun EkklesiaDialogPrev() {
    EkklesiaDialog {
        MyCommunities(
            communities = CommunityMock.getAllCommunityWithMembers(),
            onShareToCommunity = {}
        )
    }
}