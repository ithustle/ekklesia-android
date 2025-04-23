package com.toquemedia.ekklesia.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun EkklesiaButton(
    modifier: Modifier = Modifier,
    filled: Boolean = true,
    label: String,
    enabled: Boolean = true,
    loading: Boolean = false,
    onClick: () -> Unit
) {
    
    if (loading) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(32.dp),
                color = PrincipalColor,
                trackColor = PrincipalColor.copy(alpha = 0.5f),
            )
        }
    } else {
        if (filled) {
            Button(
                modifier = modifier,
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrincipalColor),
                onClick = onClick,
                enabled = enabled
            ) {
                Text(label)
            }
        } else {
            OutlinedButton(
                modifier = modifier,
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PrincipalColor),
                border = BorderStroke(width = 2.dp, color = PrincipalColor),
                onClick = onClick,
                enabled = enabled
            ) {
                Text(label)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EkklesiaButtonPrev() {
    EkklesiaButton(
        label = "Salvar",
        filled = true,
        onClick = {}
    )
}