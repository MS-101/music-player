package com.example.musicplayer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
        TextButton(
            onClick = {
                navController.navigate(SONGS_ROUTE) {
                    popUpTo(SONGS_ROUTE)
                }
            }
        ) {
            Text(
                "Songs",
                color = Color.Black
            )
        }

        TextButton(
            onClick = {
                navController.navigate(ALBUMS_ROUTE) {
                    popUpTo(ALBUMS_ROUTE)
                }
            }
        ) {
            Text(
                "Albums",
                color = Color.Black
            )
        }

        TextButton(
            onClick = {
                navController.navigate(PLAYLISTS_ROUTE) {
                    popUpTo(PLAYLISTS_ROUTE)
                }
            }
        ) {
            Text(
                "Playlists",
                color = Color.Black
            )
        }
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