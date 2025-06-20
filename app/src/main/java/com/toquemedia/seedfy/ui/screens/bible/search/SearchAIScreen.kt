package com.toquemedia.seedfy.ui.screens.bible.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.model.BiblicalResponse
import com.toquemedia.seedfy.model.StudyPlan
import com.toquemedia.seedfy.model.VerseType
import com.toquemedia.seedfy.ui.theme.PrincipalColor
import com.toquemedia.seedfy.utils.mocks.AiResponseMock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAIScreen(
    response: BiblicalResponse,
    onVerseClick: (VerseType) -> Unit,
    onSaveStudyPlan: (StudyPlan) -> Unit,
) {

    var isStudyPlanSaved by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Sua Busca:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = PrincipalColor
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = response.query,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            HorizontalDivider()
        }

        item {
            Text(
                text = "Versículos Relevantes:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = PrincipalColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                response.verses.forEach { verse ->
                    ClickableVerseCard(verse = verse, onVerseClick = onVerseClick)
                }
            }
        }

        item {
            HorizontalDivider()
        }

        item {
            Text(
                text = "Ensino Bíblico:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = PrincipalColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = response.biblicalTeaching,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            HorizontalDivider()
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        text = "Plano de Estudo:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = PrincipalColor
                    )
                    Text(
                        text = response.studyPlan.title,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "Duração: ${response.studyPlan.durationDays} dias",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(
                    onClick = {
                        isStudyPlanSaved = !isStudyPlanSaved
                        onSaveStudyPlan(response.studyPlan)
                    }
                ) {
                    Icon(
                        imageVector = if (isStudyPlanSaved) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                        contentDescription = if (isStudyPlanSaved) "Plano Salvo" else "Salvar Plano",
                        tint = if (isStudyPlanSaved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

        items(response.studyPlan.dailyReadings) { dailyReading ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Dia ${dailyReading.day}: ${dailyReading.theme}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = PrincipalColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    dailyReading.verses.forEach { verse ->
                        Column(modifier = Modifier.padding(bottom = 6.dp)) {
                            Text(
                                text = "  • ${verse.bookName} ${verse.chapter}:${verse.versicle}",
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "    \"${verse.text}\"",
                                fontSize = 13.sp,
                                lineHeight = 18.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            HorizontalDivider()
        }

        item {
            Column {
                Text(
                    text = "Para Meditação:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = PrincipalColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = response.meditationQuestion,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchAIScreenPreview() {
    SearchAIScreen(
        response = AiResponseMock.get(),
        onVerseClick = { verse -> println("Versículo clicado: ${verse.bookName} ${verse.chapter}:${verse.versicle}") },
        onSaveStudyPlan = { plan -> println("Plano de estudo salvo: ${plan.title}") }
    )
}