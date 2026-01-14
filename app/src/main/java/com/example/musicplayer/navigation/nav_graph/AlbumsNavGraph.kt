package com.example.musicplayer.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.musicplayer.navigation.ALBUMS_ROUTE
import com.example.musicplayer.navigation.Screen
import com.example.musicplayer.screens.albums.AlbumDetailScreen
import com.example.musicplayer.screens.albums.AlbumsScreen

fun NavGraphBuilder.albumsNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Albums.route,
        route = ALBUMS_ROUTE
    ) {
        composable(
            route = Screen.Albums.route
        ) {
            AlbumsScreen(
                navController = navController
            )
        }

        composable (
            route = Screen.AlbumDetail.route,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) {
            AlbumDetailScreen(
                navController = navController
            )
        }
    }
}
