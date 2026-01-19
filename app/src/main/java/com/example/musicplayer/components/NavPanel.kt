package com.example.musicplayer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
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
    Row(
        modifier = Modifier.fillMaxWidth().background(Color.Red)
    ) {
        NavPanelItem(navController, SONGS_ROUTE, "Songs")
        NavPanelItem(navController, ALBUMS_ROUTE, "Albums")
        NavPanelItem(navController, PLAYLISTS_ROUTE, "Playlists")
    }
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