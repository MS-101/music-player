package com.example.musicplayer.views.playlists

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.components.objects.PlaylistObject
import com.example.musicplayer.components.dialogs.CreatePlaylistDialog
import com.example.musicplayer.models.Playlist
import com.example.musicplayer.models.Song
import com.example.musicplayer.view_models.PlayerViewModel
import com.example.musicplayer.view_models.PlaylistsViewModel
import com.example.musicplayer.views.BaseView

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun PlaylistsView(
    navController: NavController,
    playerViewModel: PlayerViewModel? = null,
    viewModel: PlaylistsViewModel = viewModel()
) {
    val playlists = viewModel.getAllPlaylists().observeAsState(emptyList()).value

    PlaylistsViewContent(navController, playerViewModel, playlists, viewModel)
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun PlaylistsViewContent(
    navController: NavController,
    playerViewModel: PlayerViewModel? = null,
    playlists: List<Playlist>,
    viewModel: PlaylistsViewModel? = null
) {
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        CreatePlaylistDialog(
            onConfirm = { playlistName ->
                viewModel?.addPlaylist(playlistName)
                showDialog.value = false
            },
            onDismiss = {
                showDialog.value = false
            }
        )
    }

    BaseView(navController, playerViewModel) {
        Column(
            modifier = Modifier.fillMaxSize().padding(5.dp)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    showDialog.value = true
                },
            ) {
                Text("Create Playlist")
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(playlists) {
                        playlist -> PlaylistObject(navController, playlist, viewModel)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
fun PlaylistsViewPreview() {
    PlaylistsViewContent(
        rememberNavController(),
        playlists = listOf(
            Playlist(1, "Playlist 1", listOf(
                Song(1, "".toUri(), "Song 1", "Artist", 64000),
                Song(2, "".toUri(), "Song 2", "Artist", 51000)
            )),
            Playlist(2, "Playlist 2", emptyList())
        )
    )
}