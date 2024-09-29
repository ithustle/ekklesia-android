package com.toquemedia.ekklesia.ui.screens.bible.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.model.BookType
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun Book(
    modifier: Modifier = Modifier,
    book: BookType
) {
    Column(
        modifier = modifier
            .width(103.dp)
            .height(58.dp)
            .clip(shape = RoundedCornerShape(4.dp))
            .background(color = Color(PrincipalColor.value)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            book.bookName,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            "Capítulos ${book.numberOfChapters}",
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview
@Composable
private fun BookPrev() {
    Book(
        book = BookType(
            bookName = "Genesis",
            numberOfChapters = 50
        )
    )
}