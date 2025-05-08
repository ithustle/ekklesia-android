package com.toquemedia.seedfy.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WifiOff
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.ui.theme.PrincipalColor

@Composable
fun EkklesiaNoInternet(
    message: String,
    loading: Boolean = false,
    onTryAgain: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.WifiOff,
            contentDescription = null,
            tint = PrincipalColor,
            modifier = Modifier
                .size(64.dp)
        )

        Text(
            text = "Ooops!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = "$message \nVerifique sua conexão com a internet.",
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(30.dp))

        EkklesiaButton(
            label = "Tente novamente",
            loading = loading,
            onClick = onTryAgain
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EkklesiaNoInternetPrev() {
    EkklesiaNoInternet(
        message = "Sem conexão com a internet",
    )
}