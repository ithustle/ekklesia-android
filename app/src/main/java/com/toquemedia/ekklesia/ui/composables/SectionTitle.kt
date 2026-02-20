package com.toquemedia.ekklesia.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        color = PrincipalColor,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .padding(bottom = 6.dp, top = 16.dp)
            .padding(horizontal = 16.dp)
    )
}

@Preview
@Composable
private fun SectionTitlePrev() {
    SectionTitle("Comunidades")
}