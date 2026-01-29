package com.example.musicplayer.views.songs

import SongObject
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.models.Song
import com.example.musicplayer.view_models.PlayerViewModel
import com.example.musicplayer.views.BaseView
import com.example.musicplayer.view_models.SongsViewModel

@Composable
fun SongsView(
    navController: NavController,
    viewModel: SongsViewModel = viewModel(),
    playerViewModel: PlayerViewModel
) {
    val songs by viewModel.songs.observeAsState(emptyList())

    SongsViewContent(navController, songs, playerViewModel)
}

@Composable
fun SongsViewContent(
    navController: NavController,
    songs: List<Song>,
    playerViewModel: PlayerViewModel? = null
) {
    BaseView(navController, playerViewModel) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(songs) {
                index, song -> SongObject(song, onSongClick = {
                    playerViewModel?.setSongs(songs, index)
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SongsViewPreview() {
    SongsViewContent(
        rememberNavController(),
        songs = listOf(
            Song(1, "".toUri(), "Song 1", "Artist A", 64000),
            Song(2, "".toUri(), "Song 2", "Artist B", 51000)
        )
    )
}