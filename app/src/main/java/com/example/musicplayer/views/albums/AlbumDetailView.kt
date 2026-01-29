package com.example.musicplayer.views.albums

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.R
import com.example.musicplayer.components.SongObject
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
        AlbumDetailViewContent(navController, playerViewModel, album)
    }
}

@Composable
fun AlbumDetailViewContent(
    navController: NavController,
    playerViewModel: PlayerViewModel? = null,
    album: Album
) {
    BaseView(navController, playerViewModel) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().height(50.dp).background(Color.Red)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Album Image",
                    modifier = Modifier.padding(10.dp).clickable {
                        navController.popBackStack()
                    }
                )

                Text(
                    album.title,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier.padding(5.dp).basicMarquee()
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(album.songs) {
                    index, song -> SongObject(song, onClick = {
                        playerViewModel?.setSongs(album.songs, index)
                    })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumDetailViewPreview() {
    AlbumDetailViewContent(
        rememberNavController(),
        album= Album(0, "".toUri(), "Title", "Artist", listOf(
            Song(1, "".toUri(), "Song 1", "Artist A", 64000),
            Song(2, "".toUri(), "Song 2", "Artist B", 51000)
        ))
    )
}