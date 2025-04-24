package com.toquemedia.ekklesia.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun EkklesiaProgress(
    modifier: Modifier = Modifier,
    color: Color
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(32.dp),
            color = color,
            trackColor = color.copy(alpha = 0.5f),
        )
    }
}

@Preview
@Composable
private fun EkklesiaProgressPrev() {
    EkklesiaProgress(color = PrincipalColor)
}