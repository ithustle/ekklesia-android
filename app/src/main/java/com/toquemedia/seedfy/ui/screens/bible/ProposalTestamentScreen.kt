package com.toquemedia.seedfy.ui.screens.bible

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.model.BookType
import com.toquemedia.seedfy.ui.screens.bible.book.BookListScreen
import com.toquemedia.seedfy.ui.theme.PrincipalColor
import com.toquemedia.seedfy.utils.mocks.BookMock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProposalTestamentScreen(
    modifier: Modifier = Modifier,
    books: List<BookType>,
    onNavigateToChapter: (String) -> Unit = {},
    onPerformSearch: (query: String) -> Unit = {}
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabTitles = listOf(
        stringResource(R.string.old_testament),
        stringResource(R.string.new_testament)
    )

    var searchQuery by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp, bottom = 12.dp),
            placeholder = {
                Text(
                    text = stringResource(R.string.placeholder_search),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = PrincipalColor
                    )
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search_ai),
                    contentDescription = stringResource(R.string.placeholder_search),
                    tint = PrincipalColor,
                    modifier = Modifier
                        .size(24.dp)
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = null,
                            tint = PrincipalColor
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search, capitalization = KeyboardCapitalization.Sentences),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (searchQuery.isNotBlank()) {
                        onPerformSearch(searchQuery)
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                }
            )
        )

        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = PrincipalColor,
            indicator = { tabPositions ->
                if (selectedTabIndex < tabPositions.size) {
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = PrincipalColor
                    )
                }
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) PrincipalColor else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = if (selectedTabIndex == index) FontWeight.SemiBold else FontWeight.Normal,
                            fontSize = 15.sp
                        )
                    },
                )
            }
        }

        val booksToShow = when (selectedTabIndex) {
            0 -> if (books.size >= 39) books.slice(0..38) else emptyList()
            1 -> if (books.size >= 66) books.slice(39..65) else emptyList()
            else -> emptyList()
        }

        BookListScreen(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp),
            books = booksToShow,
            onNavigateToBook = { book ->
                onNavigateToChapter(book.bookName)
            }
        )
    }
}



@Preview(showBackground = true, widthDp = 380, heightDp = 720)
@Composable
private fun ProposalTestamentScreenPrev() {
    ProposalTestamentScreen(
        books = BookMock.getAll(),
        onNavigateToChapter = { bookName -> println("Navegar para capítulos de: $bookName") },
        onPerformSearch = { query -> println("Realizar busca por: $query") }
    )
}