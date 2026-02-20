package com.toquemedia.ekklesia.ui.screens.login.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun PhoneSignInButton(
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "Phone Icon",
                modifier = Modifier.size(24.dp),
                tint = PrincipalColor
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Continuar com telefone",
                fontSize = 16.sp,
                color = PrincipalColor
            )
        }
    }
}

@Preview
@Composable
private fun PhoneSignInButtonPrev() {
    PhoneSignInButton()
}