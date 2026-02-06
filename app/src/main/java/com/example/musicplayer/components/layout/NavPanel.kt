package com.example.musicplayer.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.navigation.ALBUMS_ROUTE
import com.example.musicplayer.navigation.PLAYLISTS_ROUTE
import com.example.musicplayer.navigation.SONGS_ROUTE
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@Composable
fun NavPanel(
    navController: NavController
) {
    BottomAppBar(
        containerColor = Color.Red,
        contentColor = Color.Black
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            NavPanelItem(navController, SONGS_ROUTE, "Songs")
            NavPanelItem(navController, ALBUMS_ROUTE, "Albums")
            NavPanelItem(navController, PLAYLISTS_ROUTE, "Playlists")
        }

    }

    /*
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        NavPanelItem(navController, SONGS_ROUTE, "Songs")
        NavPanelItem(navController, ALBUMS_ROUTE, "Albums")
        NavPanelItem(navController, PLAYLISTS_ROUTE, "Playlists")
    }
     */
}

@Preview(showBackground = true)
@Composable
fun NavPanelPreview() {
    MusicPlayerTheme {
        NavPanel(
            navController = rememberNavController()
        )
    }
}