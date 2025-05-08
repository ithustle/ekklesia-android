package com.toquemedia.seedfy.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.model.BookType
import com.toquemedia.seedfy.ui.theme.PrincipalColor

@Composable
fun Book(
    modifier: Modifier = Modifier,
    book: BookType,
    onNavigateToBook: (book: BookType) -> Unit = {}
) {
    Column(
        modifier = modifier
            .width(156.dp)
            .height(96.dp)
            .clip(shape = RoundedCornerShape(4.dp))
            .background(color = Color(PrincipalColor.value))
            .clickable {
                onNavigateToBook(book)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            book.bookName,
            color = Color.White,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier.height(6.dp))
        Text(
            "Capítulos ${book.numberOfChapters}",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview
@Composable
private fun BookPrev() {
    Book(
        book = BookType(
            bookName = "Lamentações de Jeremias",
            numberOfChapters = 50
        )
    )
}