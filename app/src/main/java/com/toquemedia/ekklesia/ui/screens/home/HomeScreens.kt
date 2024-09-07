package com.toquemedia.ekklesia.ui.screens.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Home(modifier: Modifier = Modifier) {
    Text("Home")
}

@Preview(showSystemUi = true)
@Composable
private fun HomePrev() {
    Home()
}