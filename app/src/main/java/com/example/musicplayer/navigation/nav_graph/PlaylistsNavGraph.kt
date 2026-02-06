package com.example.musicplayer.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.musicplayer.navigation.PLAYLISTS_ROUTE
import com.example.musicplayer.navigation.View
import com.example.musicplayer.view_models.PlayerViewModel
import com.example.musicplayer.views.playlists.PlaylistDetailView
import com.example.musicplayer.views.playlists.PlaylistsView

fun NavGraphBuilder.playlistsNavGraph(
    navController: NavHostController,
    playerViewModel: PlayerViewModel
) {
    navigation(
        startDestination = View.Playlists.route,
        route = PLAYLISTS_ROUTE
    ) {
        composable(
            route = View.Playlists.route
        ) {
            PlaylistsView(
                navController = navController,
                playerViewModel = playerViewModel
            )
        }

        composable(
            route = View.PlaylistDetail.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) {
            PlaylistDetailView(
                navController = navController,
                playerViewModel = playerViewModel
            )
        }
    }
}