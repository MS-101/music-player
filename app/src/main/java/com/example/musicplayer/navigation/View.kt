package com.example.musicplayer.navigation

const val ROOT_ROUTE = "root"
const val SONGS_ROUTE = "songs"
const val ALBUMS_ROUTE = "albums"
const val PLAYLISTS_ROUTE = "playlists"

sealed class View(val route: String) {
    object Player: View(route = "player_screen")

    object Songs: View(route = "songs_screen")

    object Albums: View(route = "albums_screen")
    object AlbumDetail: View(route = "album_detail_screen/{id}") {
        fun passId(id: Long): String {
            return this.route.replace("{id}", id.toString())
        }
    }

    object Playlists: View(route = "playlists_screen")
    object PlaylistDetail: View(route = "playlist_detail_screen/{id}") {
        fun passId(id: Int): String {
            return this.route.replace("{id}", id.toString())
        }
    }
}