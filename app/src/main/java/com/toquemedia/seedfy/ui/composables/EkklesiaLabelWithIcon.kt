package com.toquemedia.seedfy.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EkklesiaLabelWithIcon(
    modifier: Modifier = Modifier,
    label: String,
    icon: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        icon()
        Text(text = label)
    }
}

@Preview
@Composable
private fun EkklesiaLabelWithIconPrev() {
    EkklesiaLabelWithIcon(
        modifier = Modifier
            .fillMaxWidth(),
        label = "Label",
        icon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null) }
    )
}