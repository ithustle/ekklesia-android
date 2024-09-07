package com.toquemedia.ekklesia.ui.screens.today

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TodayScreen(modifier: Modifier = Modifier) {
    Text("Hoje")
}

@Preview
@Composable
private fun TodayScreenPrev() {
    TodayScreen()
}