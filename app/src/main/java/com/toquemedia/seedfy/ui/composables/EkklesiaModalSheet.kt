package com.toquemedia.seedfy.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EkklesiaModalSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismissRequest: (SheetState) -> Unit = {},
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest(sheetState) },
        sheetState = sheetState,
        modifier = Modifier.zIndex(1f)
    ) {
        Column(
            modifier = Modifier

        ) {
            content()
        }
    }
}