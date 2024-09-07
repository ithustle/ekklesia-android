package com.toquemedia.ekklesia.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.ui.screens.community.CommunityScreen
import com.toquemedia.ekklesia.ui.screens.today.TodayScreen

@Composable
fun Home(modifier: Modifier = Modifier) {

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Hoje", "Comunidade")

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = modifier.padding(8.dp),
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    height = 2.dp,
                    color = Color(0xFF6B4C75)
                )
            }
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
    Home()
}