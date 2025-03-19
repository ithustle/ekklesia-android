package com.toquemedia.ekklesia.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
    onClick: () -> Unit
) {
    if (filled) {
        Button(
            modifier = modifier,
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = PrincipalColor),
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

@Preview(showBackground = true)
@Composable
private fun EkklesiaButtonPrev() {
    EkklesiaButton(
        label = "Salvar",
        filled = false,
        onClick = {}
    )
}