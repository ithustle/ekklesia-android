package com.toquemedia.seedfy.ui.screens.bible.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.model.VerseType
import com.toquemedia.seedfy.ui.theme.PrincipalColor

@Composable
fun ClickableVerseCard(
    verse: VerseType,
    onVerseClick: (VerseType) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onVerseClick(verse) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${verse.bookName} ${verse.chapter}:${verse.versicle}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
                color = PrincipalColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = verse.text,
                fontSize = 15.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
private fun ClickableVerseCardPrev() {
    ClickableVerseCard(
        verse = VerseType(
            bookName = "Gênesis",
            chapter = 1,
            versicle = 1,
            text = "No princípio de tudo, Deus criou o céu e a terra."
        ),
        onVerseClick = {}
    )
}
