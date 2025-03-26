package com.toquemedia.ekklesia.ui.screens.bible

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.BookType
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.ui.composables.EkklesiaTopBar
import com.toquemedia.ekklesia.ui.screens.bible.book.BookListScreen
import com.toquemedia.ekklesia.ui.theme.PrincipalColor
import com.toquemedia.ekklesia.utils.mocks.BookMock

@Composable
fun TestamentScreen(
    modifier: Modifier = Modifier,
    books: List<BookType>,
    onNavigateToChapter: (String) -> Unit = {},
    currentUser: UserType? = null
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabTitles = listOf(stringResource(R.string.old_testament), stringResource(R.string.new_testament))

    Scaffold(
        topBar = {
            EkklesiaTopBar(
                title = stringResource(R.string.bible_all),
                isBackgroundTransparent = true,
                navigationBack = false,
                userAvatar = currentUser?.photo
            )
        },
        content = { innerPadding ->
            Column {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    modifier = modifier
                        .padding(innerPadding)
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
                    0 -> BookListScreen(
                        modifier = modifier.padding(horizontal = 16.dp),
                        books = books.slice(0..38),
                        onNavigateToBook = {
                            onNavigateToChapter(it.bookName)
                        }
                    )
                    1 -> BookListScreen(
                        modifier = modifier.padding(horizontal = 16.dp),
                        books = books.slice(39..65),
                        onNavigateToBook = {
                            onNavigateToChapter(it.bookName)
                        }
                    )
                }
            }
        }
    )


}

@Preview(showSystemUi = true)
@Composable
private fun TestamentScreenPrev() {
    TestamentScreen(
        books = BookMock.getAll()
    )
}