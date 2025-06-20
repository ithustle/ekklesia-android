package com.toquemedia.seedfy.ui.screens.community.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.ui.composables.EkklesiaButton
import com.toquemedia.seedfy.ui.theme.PrincipalColor

@Composable
fun CommunityMemberLeftInformation(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.community_out),
            contentDescription = null,
            modifier = Modifier
                .size(84.dp)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Você nos abandonou!",
            fontWeight = FontWeight.SemiBold,
            color = PrincipalColor,
            fontSize = 16.sp
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = "Infelizmente você tomou a decisão de seguir um caminho diferente!",
            textAlign = TextAlign.Center,
            color = Color.Gray,
        )

        Spacer(Modifier.height(20.dp))

        EkklesiaButton(
            label = "Voltar ao início",
            onClick = onGoBack
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CommunityMemberLeftInformationPrev() {
    CommunityMemberLeftInformation()
}