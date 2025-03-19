package com.toquemedia.ekklesia.ui.screens.home.today

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.ui.screens.home.composables.Mass

@Composable
fun TodayScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        Text(
            text = "Missa",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Mass(
            modifier = modifier
                .height(210.dp)
        )
        Spacer(modifier = Modifier.padding(top = 32.dp))
        Text(
            text = "Versículo do dia",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Mass(
            modifier = modifier
                .height(210.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TodayScreenPrev() {
    TodayScreen()
}