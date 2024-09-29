package com.toquemedia.ekklesia.ui.screens.bible

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.ui.screens.bible.book.BookScreen
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestamentScreen(
        modifier: Modifier = Modifier
) {
    var viewModel = TestamentViewModel(context = LocalContext.current)
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val states by viewModel.uiState.collectAsState()
    val tabTitles = listOf("Antigo", "Novo")

    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 32.dp)
    ) {

        Text(
            "Testamento",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(PrincipalColor.value)
        )

        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = modifier
                .padding(top = 8.dp)
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) Color(PrincipalColor.value) else Color.Black,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    },
                )
            }
        }

        when(selectedTabIndex) {
            0 -> BookScreen(books = states.books)
            1 -> Text("Novo")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TestamentScreenPrev() {
    TestamentScreen()
}