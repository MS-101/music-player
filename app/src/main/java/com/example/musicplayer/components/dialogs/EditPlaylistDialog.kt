package com.example.musicplayer.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.musicplayer.models.Playlist

@Composable
fun EditPlaylistDialog(
    playlist: Playlist,
    onConfirm: (String) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val name = remember { mutableStateOf(playlist.name) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Playlist - ${playlist.name}") },
        text = {
            TextField(
                value = name.value,
                onValueChange = { name.value = it },
                singleLine = false,
                label = { Text("Name:") }
            )
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(name.value) }
            ) {
                Text("Edit")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}