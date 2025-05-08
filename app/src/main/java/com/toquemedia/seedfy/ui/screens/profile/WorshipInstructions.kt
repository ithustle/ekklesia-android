package com.toquemedia.seedfy.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Diversity3
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.ui.theme.PrincipalColor

val instructionsToCreateWorship = listOf<Pair<String, ImageVector?>>(
    Pair("Vá em secção da Bíblia;", null),
    Pair("Escolha o livro, o capítulo e toque no verso desejado;", null),
    Pair("No menu de opções, escolha criar devocional;", Icons.Rounded.Diversity3),
    Pair("Escolha o tema do devocional;", null),
    Pair("Escreva o devocional;", null),
    Pair("(Opcional) Se preferir, grave também um vídeo do devocional.", null),
)

@Composable
fun WorshipInstructions(modifier: Modifier = Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Rounded.Diversity3,
            contentDescription = null,
            tint = PrincipalColor,
            modifier = Modifier
                .size(64.dp)
        )

        Text(
            text = "Nenhum devocional criado. \nSiga esses passos para criar um devocional",
            color = PrincipalColor.copy(alpha = 0.7f),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer((Modifier.height(30.dp)))

        List(instructionsToCreateWorship.size) {
            InstructionsRow(
                number = it + 1,
                instruction = instructionsToCreateWorship[it].first,
                icon = instructionsToCreateWorship[it].second
            )
        }
    }
}

@Composable
private fun InstructionsRow(
    number: Int,
    instruction: String,
    icon: ImageVector? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "${number}.",
            color = Color.DarkGray.copy(alpha = 0.7f),
            fontSize = 14.sp,
        )
        Text(
            text = instruction,
            color = Color.DarkGray.copy(alpha = 0.7f),
            fontSize = 14.sp,
        )

        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = PrincipalColor.copy(alpha = 0.7f),
                modifier = Modifier
                    .size(18.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WorshipInstructionsPrev() {
    WorshipInstructions(modifier = Modifier.fillMaxWidth())
}