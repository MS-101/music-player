package com.example.musicplayer.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.musicplayer.navigation.PLAYLISTS_ROUTE
import com.example.musicplayer.navigation.Screen
import com.example.musicplayer.screens.playlists.PlaylistDetailScreen
import com.example.musicplayer.screens.playlists.PlaylistsScreen

fun NavGraphBuilder.playlistsNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Songs.route,
        route = PLAYLISTS_ROUTE
    ) {
        composable(
            route = Screen.Playlists.route
        ) {
            PlaylistsScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.PlaylistDetail.route,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) {
            PlaylistDetailScreen(
                navController = navController
            )
        }
    }
}