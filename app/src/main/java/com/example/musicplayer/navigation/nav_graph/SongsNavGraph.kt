package com.example.musicplayer.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.musicplayer.navigation.SONGS_ROUTE
import com.example.musicplayer.navigation.Screen
import com.example.musicplayer.screens.songs.SongDetailScreen
import com.example.musicplayer.screens.songs.SongsScreen

fun NavGraphBuilder.songsNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Songs.route,
        route = SONGS_ROUTE
    ) {
        composable(
            route = Screen.Songs.route
        ) {
            SongsScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.SongDetail.route,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) {
            SongDetailScreen(
                navController = navController
            )
        }
    }
}