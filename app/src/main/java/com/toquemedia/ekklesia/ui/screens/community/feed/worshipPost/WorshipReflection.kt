package com.toquemedia.ekklesia.ui.screens.community.feed.worshipPost

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun WorshipReflection(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    reflection: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Reflexão",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = backgroundColor
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = reflection,
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WorshipReflectionPrev() {
    WorshipReflection(
        backgroundColor = PrincipalColor,
        reflection = "Devocional é um termo que vem do latim devotio, que significa dedicação, entrega, consagração. O devocional é uma expressão de amor e obediência a Deus, que demonstra o nosso desejo de estar em comunhão com ele e de honrá-lo com a nossa vida.\n" +
                "\n" +
                "Contudo, a prática de devocional não deve ser feito como uma obrigação ou um ritual religioso, mas uma oportunidade de desfrutar da presença de Deus e de aprender com Ele, nossa fonte de vida eterna {João 17:3}. "
    )
}