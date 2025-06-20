package com.toquemedia.seedfy.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.ui.theme.PrincipalColor

@Composable
fun EkklesiaGenerating(
    modifier: Modifier = Modifier,
    label: String
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))

        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = PrincipalColor
        )

        EkklesiaProgress(color = PrincipalColor)

        Spacer(Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
private fun EkklesiaGeneratingPrev() {
    EkklesiaGenerating(
        modifier = Modifier
            .fillMaxSize(),
        label = "A analisar uma explicação..."
    )
}