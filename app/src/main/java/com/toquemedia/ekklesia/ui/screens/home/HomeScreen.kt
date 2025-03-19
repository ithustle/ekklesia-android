package com.toquemedia.ekklesia.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.ui.screens.home.community.CommunityScreen
import com.toquemedia.ekklesia.ui.screens.home.today.TodayScreen

@Composable
fun HomeScreen() {

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Hoje", "Comunidade")

    Column {
        TabRow (
            selectedTabIndex = selectedTabIndex,
            backgroundColor = MaterialTheme.colors.background,
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) Color(0xFF6B4C75) else Color.Black,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    }
                )
            }
        }

        when(selectedTabIndex) {
            0 -> TodayScreen()
            1 -> CommunityScreen()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomePrev() {
    HomeScreen()
}