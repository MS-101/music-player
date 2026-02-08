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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.R
import com.example.musicplayer.components.dialogs.AddToPlaylistDialogue
import com.example.musicplayer.models.Album
import com.example.musicplayer.navigation.View
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.example.musicplayer.view_models.PlaylistsViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun AlbumObject(
    navController: NavController,
    album: Album,
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
                        album.songs.forEach { song ->
                            playlistsViewModel.addSongToPlaylist(song, playlist)
                        }
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
            Row(
                modifier = Modifier.weight(1f).clickable {
                    navController.navigate(
                        View.AlbumDetail.passId(album.id)
                    )
                }
            ) {
                if (album.thumbnail != null) {
                    Image(
                        bitmap = album.thumbnail.asImageBitmap(),
                        contentDescription = "Album Image",
                        modifier = Modifier.width(100.dp).fillMaxHeight()
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.album),
                        contentDescription = "Album Image",
                        modifier = Modifier.width(100.dp).fillMaxHeight()
                    )
                }

                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.weight(1f).fillMaxHeight().padding(5.dp)
                ) {
                    Text(
                        text = album.title,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        maxLines = 1,
                        modifier = Modifier
                            .basicMarquee()
                    )
                    Text(
                        text = album.artist,
                        fontSize = 20.sp
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
                        text = { Text("Add To Playlist") },
                        onClick = {
                            showAddToPlaylistDialog.value = true
                            dropdownExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
fun AlbumObjectPreview() {
    MusicPlayerTheme {
        AlbumObject(
            rememberNavController(),
            Album(0, "".toUri(), "Title", "Artist", emptyList())
        )
    }
}