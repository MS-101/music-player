package com.example.musicplayer.components.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.example.musicplayer.view_models.PlayerViewModel

@Composable
fun BottomBar(
    navController: NavController,
    playerViewModel: PlayerViewModel? = null
) {
    Column {
        if (playerViewModel != null) {
            val song by playerViewModel.song

            if (song != null) {
                PlayerPanel(
                    navController = navController,
                    viewModel = playerViewModel
                )
            }
        }

        NavPanel(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    MusicPlayerTheme {
        BottomBar(
            navController = rememberNavController()
        )
    }
}