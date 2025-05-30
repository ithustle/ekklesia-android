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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.model.BiblicalResponse
import com.toquemedia.seedfy.model.DailyReading
import com.toquemedia.seedfy.model.StudyPlan
import com.toquemedia.seedfy.model.VerseType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAIScreen(
    response: BiblicalResponse,
    onVerseClick: (VerseType) -> Unit,
    onSaveStudyPlan: (StudyPlan) -> Unit
) {

    var isStudyPlanSaved by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp) // Aumenta o espaçamento geral
    ) {
        item {
            Column {
                Text(
                    text = "Sua Busca:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
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
                color = MaterialTheme.colorScheme.onSurface
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
                color = MaterialTheme.colorScheme.onSurface
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
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = response.studyPlan.title,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
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
                    androidx.compose.material3.Icon(
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
                        color = MaterialTheme.colorScheme.onSurface
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
                    color = MaterialTheme.colorScheme.onSurface
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
    val sampleResponse = BiblicalResponse(
        query = "Qual o significado da Páscoa?",
        verses = listOf(
            VerseType("Êxodo", 12, 1, "Disse o Senhor a Moisés e a Arão na terra do Egito..."),
            VerseType("Êxodo", 12, 14, "E este dia vos será por memorial, e celebrá-lo-eis como festa ao Senhor..."),
            VerseType("1 Coríntios", 5, 7, "Alimpai-vos, pois, do fermento velho, para que sejais uma nova massa, assim como estais sem fermento. Porque Cristo, nossa Páscoa, foi sacrificado por nós.")
        ),
        biblicalTeaching = "A Páscoa é uma festa judaica que comemora a libertação dos israelitas da escravidão no Egito. No Novo Testamento, Jesus Cristo é identificado como o Cordeiro Pascal, cujo sacrifício nos liberta do pecado. A Páscoa aponta para a redenção e a nova vida em Cristo, sendo um memorial da graça de Deus.",
        studyPlan = StudyPlan(
            title = "Estudo sobre a Páscoa",
            durationDays = 3,
            dailyReadings = listOf(
                DailyReading(
                    1, "A Páscoa no Antigo Testamento", listOf(
                        VerseType("Êxodo", 12, 1, "Disse o Senhor a Moisés e a Arão na terra do Egito..."),
                        VerseType("Êxodo", 12, 20, "Nenhuma coisa levedada comereis; em todas as vossas habitações comereis pães ázimos.")
                    )
                ),
                DailyReading(
                    2, "Jesus como Cordeiro Pascal", listOf(
                        VerseType("João", 1, 29, "No dia seguinte, João viu a Jesus, que vinha para ele, e disse: Eis o Cordeiro de Deus, que tira o pecado do mundo!"),
                        VerseType("1 Coríntios", 5, 7, "Alimpai-vos, pois, do fermento velho, para que sejais uma nova massa, assim como estais sem fermento. Porque Cristo, nossa Páscoa, foi sacrificado por nós.")
                    )
                ),
                DailyReading(
                    3, "O Significado da Páscoa para Nós Hoje", listOf(
                        VerseType("Gálatas", 3, 13, "Cristo nos resgatou da maldição da lei, fazendo-se maldição por nós; porque está escrito: Maldito todo aquele que for pendurado no madeiro;"),
                        VerseType("Romanos", 6, 4, "De sorte que fomos sepultados com ele pelo batismo na morte; para que, como Cristo foi ressuscitado dentre os mortos pela glória do Pai, assim andemos nós também em novidade de vida.")
                    )
                )
            )
        ),
        meditationQuestion = "Como a celebração da Páscoa e o sacrifício de Jesus me inspiram a viver uma vida de gratidão e liberdade em Cristo?"
    )
    SearchAIScreen(
        response = sampleResponse,
        onVerseClick = { verse -> println("Versículo clicado: ${verse.bookName} ${verse.chapter}:${verse.versicle}") },
        onSaveStudyPlan = { plan -> println("Plano de estudo salvo: ${plan.title}") }
    )
}