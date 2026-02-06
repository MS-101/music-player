package com.example.musicplayer.components.objects

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.R
import com.example.musicplayer.components.dialogs.DeletePlaylistDialog
import com.example.musicplayer.components.dialogs.EditPlaylistDialog
import com.example.musicplayer.models.Playlist
import com.example.musicplayer.navigation.View
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.example.musicplayer.view_models.PlaylistsViewModel

@Composable
fun PlaylistObject(
    navController: NavController,
    playlist: Playlist,
    viewModel: PlaylistsViewModel? = null
) {
    val showEditDialog = remember { mutableStateOf(false) }
    if (showEditDialog.value) {
        EditPlaylistDialog(
            playlist,
            onConfirm = { name ->
                playlist.name = name
                viewModel?.updatePlaylist(playlist)
                showEditDialog.value = false
            },
            onDismiss = {
                showEditDialog.value = false
            }
        )
    }

    val showDeleteDialog = remember { mutableStateOf(false) }
    if (showDeleteDialog.value) {
        DeletePlaylistDialog(
            playlist,
            onConfirm = {
                viewModel?.deletePlaylist(playlist)
                showDeleteDialog.value = false
            },
            onDismiss = {
                showDeleteDialog.value = false
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier.padding(5.dp)
        ) {
            Row(
                modifier = Modifier.weight(1f).clickable {
                    navController.navigate(
                        View.PlaylistDetail.passId(playlist.id)
                    )
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.album),
                    contentDescription = "Playlist Image",
                    modifier = Modifier.width(100.dp).fillMaxHeight()
                )

                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.weight(1f).fillMaxHeight().padding(5.dp)
                ) {
                    Text(
                        text = playlist.name,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        maxLines = 1,
                        modifier = Modifier.basicMarquee()
                    )

                    Text(
                        text = "0 songs",
                        fontSize = 20.sp,
                    )
                }
            }

            Column()
            {
                var dropdownExpanded by remember { mutableStateOf(false) }

                IconButton(
                    modifier = Modifier.width(50.dp).fillMaxHeight(),
                    onClick = { dropdownExpanded = true }
                ) {
                    Icon(
                        painterResource(id = R.drawable.more),
                        contentDescription = "Playlist Image",
                    )
                }

                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Edit") },
                        onClick = {
                            dropdownExpanded = false
                            showEditDialog.value = true
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Delete") },
                        onClick = {
                            dropdownExpanded = false
                            showDeleteDialog.value = true
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaylistObjectPreview() {
    MusicPlayerTheme {
        PlaylistObject(
            rememberNavController(),
            Playlist(0, "Playlist 1")
        )
    }
}