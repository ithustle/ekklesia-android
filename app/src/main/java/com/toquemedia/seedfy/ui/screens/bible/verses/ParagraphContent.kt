package com.toquemedia.seedfy.ui.screens.bible.verses

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.ui.theme.PrincipalColor

@Composable
fun ParagraphContent(
    paragraph: String,
    isMarked: Boolean,
    hasNote: Boolean,
    hasWorship: Boolean,
    isSelected: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = paragraph,
            fontSize = 17.sp,
            color = if (isMarked) Color.White else PrincipalColor,
            textDecoration = if (isSelected) TextDecoration.Underline else TextDecoration.None,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )

        if (hasNote || hasWorship) {
            IconRow(hasNote = hasNote, hasWorship = hasWorship)
        }
    }
}

@Preview
@Composable
private fun ParagraphContentPrev() {
    ParagraphContent(
        paragraph = "Observem o mês de abibe e celebrem a Páscoa do Senhor, do seu Deus, pois no mês de abibe, de noite, ele os tirou do Egito.",
        isMarked = true,
        hasNote = true,
        hasWorship = true,
        isSelected = true
    )
}