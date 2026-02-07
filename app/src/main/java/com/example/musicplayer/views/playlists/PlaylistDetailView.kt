package com.example.musicplayer.views.playlists

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.components.objects.SongObject
import com.example.musicplayer.models.Playlist
import com.example.musicplayer.models.Song
import com.example.musicplayer.view_models.PlayerViewModel
import com.example.musicplayer.view_models.PlaylistsViewModel
import com.example.musicplayer.views.BaseView

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun PlaylistDetailView(
    navController: NavController,
    playerViewModel: PlayerViewModel? = null,
    viewModel: PlaylistsViewModel = viewModel()
) {
    val id = navController.currentBackStackEntry?.arguments?.getLong("id") ?: 0

    val playlist = viewModel.getPlaylist(id).observeAsState().value

    if (playlist != null) {
        PlaylistDetailViewContent(
            navController,
            playerViewModel,
            playlist
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDetailViewContent(
    navController: NavController,
    playerViewModel: PlayerViewModel? = null,
    playlist: Playlist
) {
    BaseView(
        navController,
        playerViewModel,
        playlist.name
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(5.dp)
        ) {
            itemsIndexed(playlist.songs) {
                index, song -> SongObject(song, onClick = {
                    playerViewModel?.setSongs(playlist.songs, index)
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaylistDetailViewPreview() {
    PlaylistDetailViewContent(
        rememberNavController(),
        playlist = Playlist(0, "Playlist", listOf(
            Song(1, "".toUri(), "Song 1", "Artist", 64000),
            Song(2, "".toUri(), "Song 2", "Artist", 51000)
        ))
    )
}