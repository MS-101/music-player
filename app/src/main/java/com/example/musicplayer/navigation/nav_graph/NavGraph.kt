package com.example.musicplayer.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.musicplayer.navigation.ROOT_ROUTE
import com.example.musicplayer.navigation.SONGS_ROUTE

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = SONGS_ROUTE,
        route = ROOT_ROUTE
    ) {
        songsNavGraph(navController)
        albumsNavGraph(navController)
        playlistsNavGraph(navController)
    }
}