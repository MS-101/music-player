package com.example.musicplayer.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.musicplayer.navigation.ALBUMS_ROUTE
import com.example.musicplayer.navigation.View
import com.example.musicplayer.view_models.PlayerViewModel
import com.example.musicplayer.views.albums.AlbumDetailView
import com.example.musicplayer.views.albums.AlbumsView

fun NavGraphBuilder.albumsNavGraph(
    navController: NavHostController,
    playerViewModel: PlayerViewModel
) {
    navigation(
        startDestination = View.Albums.route,
        route = ALBUMS_ROUTE
    ) {
        composable(
            route = View.Albums.route
        ) {
            AlbumsView(
                navController = navController,
                playerViewModel = playerViewModel
            )
        }

        composable (
            route = View.AlbumDetail.route,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) {
            AlbumDetailView(
                navController = navController,
                playerViewModel = playerViewModel
            )
        }
    }
}
