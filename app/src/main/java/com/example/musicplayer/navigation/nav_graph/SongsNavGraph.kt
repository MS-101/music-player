package com.example.musicplayer.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.musicplayer.navigation.SONGS_ROUTE
import com.example.musicplayer.navigation.View
import com.example.musicplayer.views.songs.SongDetailView
import com.example.musicplayer.views.songs.SongsView

fun NavGraphBuilder.songsNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = View.Songs.route,
        route = SONGS_ROUTE
    ) {
        composable(
            route = View.Songs.route
        ) {
            SongsView(
                navController = navController
            )
        }

        composable(
            route = View.SongDetail.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) {
            SongDetailView(
                navController = navController
            )
        }
    }
}