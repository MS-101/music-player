package com.example.musicplayer.views.albums

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
import com.example.musicplayer.models.Album
import com.example.musicplayer.models.Song
import com.example.musicplayer.view_models.AlbumsViewModel
import com.example.musicplayer.view_models.PlayerViewModel
import com.example.musicplayer.views.BaseView

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun AlbumDetailView(
    navController: NavController,
    playerViewModel: PlayerViewModel? = null,
    viewModel: AlbumsViewModel = viewModel()
) {
    val id = navController.currentBackStackEntry?.arguments?.getLong("id") ?: 0

    val albums by viewModel.albums.observeAsState(emptyList())
    val album = albums.firstOrNull { it.id == id }

    if (album != null) {
        AlbumDetailViewContent(
            navController,
            playerViewModel,
            album
        )
    }
}

@Composable
fun AlbumDetailViewContent(
    navController: NavController,
    playerViewModel: PlayerViewModel? = null,
    album: Album
) {
    BaseView(
        navController,
        playerViewModel,
        album.title
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(5.dp)
        ) {
            itemsIndexed(album.songs) {
                index, song -> SongObject(song, onClick = {
                    playerViewModel?.setSongs(album.songs, index)
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumDetailViewPreview() {
    AlbumDetailViewContent(
        rememberNavController(),
        album = Album(0, "".toUri(), "Album", "Artist", listOf(
            Song(1, "".toUri(), "Song 1", "Artist", 64000),
            Song(2, "".toUri(), "Song 2", "Artist", 51000)
        ))
    )
}