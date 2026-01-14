package com.example.musicplayer.navigation

const val ROOT_ROUTE = "root"
const val SONGS_ROUTE = "songs"
const val ALBUMS_ROUTE = "albums"
const val PLAYLISTS_ROUTE = "playlists"

sealed class Screen(val route: String) {
    object Songs: Screen(route = "songs_screen")
    object SongDetail: Screen(route = "song_detail_screen/{id}") {
        fun passId(id: Int): String {
            return this.route.replace("{id}", id.toString())
        }
    }

    object Albums: Screen(route = "albums_screen")
    object AlbumDetail: Screen(route = "album_detail_screen/{id}") {
        fun passId(id: Int): String {
            return this.route.replace("{id}", id.toString())
        }
    }

    object Playlists: Screen(route = "playlists_screen")
    object PlaylistDetail: Screen(route = "playlist_detail_screen/{id}") {
        fun passId(id: Int): String {
            return this.route.replace("{id}", id.toString())
        }
    }
}