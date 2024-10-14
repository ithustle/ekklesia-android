package com.toquemedia.ekklesia.ui.screens.bible.verses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.ui.screens.bible.states.TestamentUiState
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun VersesScreen(
    modifier: Modifier = Modifier,
    states: TestamentUiState,
    chapterNumber: String?
) {

    val book = states.book

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.padding(vertical = 32.dp))
        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = book?.bookName.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrincipalColor
            )
            Text(
                text = chapterNumber.toString(),
                fontSize = 40.sp,
                color = PrincipalColor
            )

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            states.book?.
                verses?.get((chapterNumber?.toInt()?.minus(1)) ?: 0)?.forEachIndexed { line, verse ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = (line + 1).toString(),
                            fontSize = 20.sp,
                            color = PrincipalColor,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = modifier.padding(horizontal = 4.dp))
                        Text(
                            text = verse,
                            fontSize = 20.sp,
                            color = PrincipalColor,
                        )
                    }
                }
        }
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun VersesScreenPrev() {
    VersesScreen(states = TestamentUiState(), chapterNumber = "2")
}