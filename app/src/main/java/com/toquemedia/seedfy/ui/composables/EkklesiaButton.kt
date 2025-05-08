package com.toquemedia.seedfy.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.seedfy.ui.theme.PrincipalColor

@Composable
fun EkklesiaButton(
    modifier: Modifier = Modifier,
    filled: Boolean = true,
    label: String,
    color: Color = PrincipalColor,
    enabled: Boolean = true,
    loading: Boolean = false,
    onClick: () -> Unit
) {
    
    if (loading) {
        EkklesiaProgress(color = color)
    } else {
        if (filled) {
            Button(
                modifier = modifier,
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = color),
                onClick = onClick,
                enabled = enabled
            ) {
                Text(label)
            }
        } else {
            OutlinedButton(
                modifier = modifier,
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = color),
                border = BorderStroke(width = 2.dp, color = color),
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