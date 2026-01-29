package com.example.musicplayer.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicplayer.navigation.ROOT_ROUTE
import com.example.musicplayer.navigation.SONGS_ROUTE
import com.example.musicplayer.navigation.View
import com.example.musicplayer.view_models.PlayerViewModel
import com.example.musicplayer.views.PlayerView

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    playerViewModel: PlayerViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = SONGS_ROUTE,
        route = ROOT_ROUTE
    ) {
        composable(
            route = View.Player.route
        ) {
            PlayerView(navController, playerViewModel)
        }

        songsNavGraph(navController, playerViewModel)
        albumsNavGraph(navController)
        playlistsNavGraph(navController)
    }
}
