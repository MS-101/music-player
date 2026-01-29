package com.example.musicplayer.views.playlists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.view_models.PlayerViewModel
import com.example.musicplayer.views.BaseView

@Composable
fun PlaylistDetailView(
    navController: NavController,
    playerViewModel: PlayerViewModel? = null
) {
    val id = navController.currentBackStackEntry?.arguments?.getInt("id") ?: 0

    BaseView(navController, playerViewModel) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Playlist - $id",
                color = Color.Red,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
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
fun PlaylistDetailViewPreview() {
    PlaylistDetailView(
        rememberNavController()
    )
}