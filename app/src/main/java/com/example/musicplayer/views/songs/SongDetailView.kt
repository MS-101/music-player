package com.example.musicplayer.views.songs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.models.Song
import com.example.musicplayer.views.BaseView
import com.example.musicplayer.utils.toMmSs
import com.example.musicplayer.view_models.SongsViewModel

@Composable
fun SongDetailView(
    navController: NavController,
    viewModel: SongsViewModel = viewModel()
) {
    val id = navController.currentBackStackEntry?.arguments?.getLong("id") ?: 0

    val songs by viewModel.songs.observeAsState(emptyList())
    val song = songs.firstOrNull { it.id == id }

    if (song != null) {
        SongDetailViewContent(navController, song)
    }
}

@Composable
fun SongDetailViewContent(
    navController: NavController,
    song: Song
) {
    /*
    val exoplayer = ExoPlayer.Builder(LocalContext.current).build().apply {
        setMediaItem(MediaItem.fromUri(song.uri))
        prepare()
        play()
    }
    */

    BaseView(navController) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                song.title,
                color = Color.Red,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                song.artist,
                fontSize = 20.sp,
            )

            Text(
                song.duration.toMmSs(),
                fontSize = 20.sp,
            )

            Button(
                {
                    navController.popBackStack()
                }
            ) {
                Text("Return")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SongDetailViewPreview() {
    SongDetailViewContent(
        navController = rememberNavController(),
        Song(0,"".toUri(),"Title","Artist",38000)
    )
}