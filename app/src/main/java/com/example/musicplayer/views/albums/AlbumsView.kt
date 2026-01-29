package com.example.musicplayer.views.albums

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.components.AlbumObject
import com.example.musicplayer.models.Album
import com.example.musicplayer.view_models.AlbumsViewModel
import com.example.musicplayer.view_models.PlayerViewModel
import com.example.musicplayer.views.BaseView

@Composable
fun AlbumsView(
    navController: NavController,
    playerViewModel: PlayerViewModel,
    viewModel: AlbumsViewModel = viewModel(),
) {
    val albums by viewModel.albums.observeAsState(emptyList())

    AlbumsViewContent(navController, playerViewModel, albums)
}

@Composable
fun AlbumsViewContent(
    navController: NavController,
    playerViewModel: PlayerViewModel? = null,
    albums: List<Album>,
) {
    BaseView(navController, playerViewModel) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(albums) {
                album -> AlbumObject(navController, album)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumsViewPreview() {
    AlbumsViewContent(
        rememberNavController(),
        albums = listOf(
            Album(1, "".toUri(), "Album 1", "Artist A", emptyList()),
            Album(2, "".toUri(), "Album 2", "Artist B", emptyList())
        )
    )
}