package com.toquemedia.seedfy.ui.screens.biblePlan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.seedfy.model.StudyPlan
import com.toquemedia.seedfy.utils.mocks.AiResponseMock

@Composable
fun BiblePlanListScreen(
    plans: List<StudyPlan>,
    onNavigateToBiblePlan: (StudyPlan) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn {
            items(plans) { plan ->
                Spacer(Modifier.height(16.dp))
                BiblePlanItem(
                    title = plan.title,
                    daysCount = plan.durationDays,
                    progress = (plan.progress.toFloat() / plan.durationDays.toFloat()),
                    onClick = {
                        onNavigateToBiblePlan(plan)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BiblePlanListScreenPrev() {
    BiblePlanListScreen(
        plans = listOf(AiResponseMock.get().studyPlan)
    )
}