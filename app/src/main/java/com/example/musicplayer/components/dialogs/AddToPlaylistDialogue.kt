package com.example.musicplayer.components.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicplayer.models.Playlist

@Composable
fun AddToPlaylistDialogue(
    playlists: List<Playlist>?,
    preselected: List<Playlist> = emptyList(),
    onConfirm: (List<Playlist>) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val selected = remember {
        mutableStateListOf<Playlist>().apply {
            addAll(preselected)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add to Playlist") },

        text = {
            Column {
                playlists?.forEach { playlist ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (playlist in selected) {
                                    selected.remove(playlist)
                                } else {
                                    selected.add(playlist)
                                }
                            }
                            .padding(vertical = 6.dp)
                    ) {
                        Checkbox(
                            checked = playlist in selected,
                            onCheckedChange = { isChecked ->
                                if (isChecked) selected.add(playlist)
                                else selected.remove(playlist)
                            }
                        )
                        Text(playlist.name)
                    }
                }
            }
        },

        confirmButton = {
            Button(onClick = { onConfirm(selected.toList()) }) {
                Text("Add")
            }
        },

        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
