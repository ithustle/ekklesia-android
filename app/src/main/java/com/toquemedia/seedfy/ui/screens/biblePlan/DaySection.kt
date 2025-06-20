package com.toquemedia.seedfy.ui.screens.biblePlan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.toquemedia.seedfy.model.DailyReading
import com.toquemedia.seedfy.ui.theme.PrincipalColor
import com.toquemedia.seedfy.utils.mocks.AiResponseMock

@Composable
fun DaySection(
    dailyVerse: DailyReading,
    wasRead: Boolean = false,
    onCheckAsRead: (Boolean, Int) -> Unit = { _, _ ->}
) {

    var wasRead by remember { mutableStateOf(wasRead) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    wasRead = !wasRead
                    onCheckAsRead(wasRead, dailyVerse.day)
                },
            shape = RoundedCornerShape(1.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = PrincipalColor,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dailyVerse.day.toString(),
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Dia ${dailyVerse.day}",
                        color = PrincipalColor.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    Text(
                        text = dailyVerse.theme,
                        color = PrincipalColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Icon(
                    imageVector = if (wasRead) Icons.Rounded.CheckCircle else Icons.Rounded.CheckCircleOutline,
                    contentDescription = null,
                    tint = if (wasRead) PrincipalColor else Color.Gray
                )
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))

        if (!wasRead) {
            dailyVerse.verses.forEach { verse ->
                VerseCard(verse)
            }
        }
    }
}

@Preview
@Composable
private fun DaySectionPrev() {
    DaySection(dailyVerse = AiResponseMock.get().studyPlan.dailyReadings.first())
}