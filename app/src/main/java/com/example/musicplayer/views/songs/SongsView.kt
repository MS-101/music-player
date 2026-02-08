package com.example.musicplayer.views.songs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.components.objects.SongObject
import com.example.musicplayer.models.Song
import com.example.musicplayer.view_models.PlayerViewModel
import com.example.musicplayer.view_models.PlaylistsViewModel
import com.example.musicplayer.views.BaseView
import com.example.musicplayer.view_models.SongsViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun SongsView(
    navController: NavController,
    playerViewModel: PlayerViewModel,
    playlistsViewModel: PlaylistsViewModel = viewModel(),
    viewModel: SongsViewModel = viewModel()
) {
    val songs by viewModel.songs.observeAsState(emptyList())

    SongsViewContent(navController, playerViewModel, playlistsViewModel, songs)
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun SongsViewContent(
    navController: NavController,
    playerViewModel: PlayerViewModel? = null,
    playlistsViewModel: PlaylistsViewModel? = null,
    songs: List<Song>
) {
    BaseView(navController, playerViewModel) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(5.dp)
        ) {
            itemsIndexed(songs) {
                index, song -> SongObject(song, playlistsViewModel = playlistsViewModel, onClick = {
                    playerViewModel?.setSongs(songs, index)
                })
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
fun SongsViewPreview() {
    SongsViewContent(
        rememberNavController(),
        songs = listOf(
            Song(0, "".toUri(), "Song 1", "Artist A", 64000),
            Song(1, "".toUri(), "Song 2", "Artist B", 51000)
        )
    )
}