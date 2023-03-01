package com.tinyspace.tinytask.counter

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable


@Composable
fun WarningDialog(onCancel: () -> Unit) {
    AlertDialog(confirmButton = {
        TextButton(onClick = {

            onCancel()
        }) {
            Text("Cancel")
        }
    },
        onDismissRequest = {},
        text = {
            Column {
                Text("Are you sure want to cancel?")
                Text("The progress will not be saved")
                Text("Put back the phone to keep the focus")
            }
        }
    )
}