package com.radjamahesaw0054.belanjabijak.ui.screen


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.radjamahesaw0054.belanjabijak.R


@Composable
fun DisplayAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
){
    AlertDialog(
        text = {
            Text(
                text = stringResource(R.string.message_delete)
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirmation() }
            ) {
                Text(
                    text = stringResource(R.string.btn_delete)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() }
            ) {
                Text(
                    text = stringResource(R.string.btn_cancel)
                )
            }
        },
        onDismissRequest = { onDismissRequest() }
    )
}

