package com.toquemedia.ekklesia.ui.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.toquemedia.ekklesia.R

@Composable
fun EkklesiaDialog(
    modifier: Modifier = Modifier,
    dialogTitle: String,
    dialogText: String,
    confirmText: String? = null,
    dismissText: String? = null,
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {}
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(confirmText ?: stringResource(R.string.yes_confirmation))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(dismissText ?: stringResource(R.string.cancelar))
            }
        }
    )

}

@Preview
@Composable
private fun EkklesiaDialogPrev() {
    EkklesiaDialog(
        dialogTitle = "Eliminar comunidades",
        dialogText = "Você deseja realmente eliminar a comunidade?",
        onDismissRequest = {},
        onConfirmation = {}
    )
}