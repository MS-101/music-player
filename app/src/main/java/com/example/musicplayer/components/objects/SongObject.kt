package com.example.musicplayer.components.objects

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.musicplayer.R
import com.example.musicplayer.components.dialogs.AddToPlaylistDialogue
import com.example.musicplayer.models.Playlist
import com.example.musicplayer.models.Song
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.example.musicplayer.utils.toMmSs
import com.example.musicplayer.view_models.PlaylistsViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun SongObject(
    song: Song,
    playlist: Playlist? = null,
    onClick: () -> Unit = {},
    playlistsViewModel: PlaylistsViewModel? = null
) {
    val showAddToPlaylistDialog = remember { mutableStateOf(false) }

    if (playlistsViewModel != null) {
        if (showAddToPlaylistDialog.value) {
            val playlists = playlistsViewModel.getAllPlaylists().observeAsState(emptyList()).value

            AddToPlaylistDialogue(
                playlists = playlists,
                onConfirm = { selectedPlaylists ->
                    selectedPlaylists.forEach { playlist ->
                        playlistsViewModel.addSongToPlaylist(song, playlist)
                    }

                    showAddToPlaylistDialog.value = false
                },
                onDismiss = {
                    showAddToPlaylistDialog.value = false
                }
            )
        }
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
            Row (
                modifier = Modifier.weight(1f).clickable {
                    onClick()
                }
            )
            {
                if (song.thumbnail != null) {
                    Image(
                        bitmap = song.thumbnail.asImageBitmap(),
                        contentDescription = "Song Image",
                        modifier = Modifier.width(100.dp).fillMaxHeight()
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.song),
                        contentDescription = "Song Image",
                        modifier = Modifier.width(100.dp).fillMaxHeight()
                    )
                }

                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.weight(1f).fillMaxHeight().padding(5.dp)
                ) {
                    Text(
                        text = song.title,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        maxLines = 1,
                        modifier = Modifier
                            .basicMarquee()
                    )
                    Text(
                        text = song.artist,
                        fontSize = 20.sp
                    )
                    Text(
                        text = song.duration.toMmSs()
                    )
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

                    if (playlistsViewModel != null) {
                        DropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false }
                        ) {
                            if (playlist == null) {
                                DropdownMenuItem(
                                    text = { Text("Add To Playlist") },
                                    onClick = {
                                        showAddToPlaylistDialog.value = true
                                        dropdownExpanded = false
                                    }
                                )
                            } else {
                                DropdownMenuItem(
                                    text = { Text("Remove From Playlist") },
                                    onClick = {
                                        playlistsViewModel.removeSongFromPlaylist(song, playlist)

                                        dropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
fun SongObjectPreview() {
    MusicPlayerTheme {
        SongObject(
            Song(0, "".toUri(), "Title", "Artist", 38000)
        )
    }
}