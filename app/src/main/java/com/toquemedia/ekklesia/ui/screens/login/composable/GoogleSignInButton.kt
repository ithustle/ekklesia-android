package com.toquemedia.ekklesia.ui.screens.login.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.R

@Composable
fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    onClickLogin: () -> Unit = {}
) {
    Button(
        onClick = onClickLogin,
        modifier = modifier
            .fillMaxWidth(0.8f)
            .height(56.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(28.dp)),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White
        ),
        shape = RoundedCornerShape(28.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.devotion_social_network_logo),
                contentDescription = "Google Logo",
                modifier = modifier.size(24.dp)
            )
            Spacer(modifier = modifier.width(12.dp))
            Text(
                text = "Continuar com Google",
                color = Color.DarkGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
private fun GoogleSignInButtonPrev() {
    GoogleSignInButton()
}