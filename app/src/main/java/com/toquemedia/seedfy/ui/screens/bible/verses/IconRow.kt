package com.toquemedia.seedfy.ui.screens.bible.verses

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.seedfy.ui.theme.PrincipalColor

@Composable
fun IconRow(hasNote: Boolean, hasWorship: Boolean) {
    Row {
        if (hasNote) {
            Icon(
                imageVector = Icons.Default.EditNote,
                contentDescription = null,
                tint = PrincipalColor,
                modifier = Modifier.size(20.dp)
            )
        }
        if (hasWorship) {
            Icon(
                imageVector = Icons.Default.Diversity3,
                contentDescription = null,
                tint = PrincipalColor,
                modifier = Modifier.size(20.dp))
        }
    }
}

@Preview
@Composable
private fun IconRowPrev() {
    IconRow(hasNote = true, hasWorship = true)
}