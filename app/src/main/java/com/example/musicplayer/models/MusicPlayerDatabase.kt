package com.example.musicplayer.models

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Playlist::class], version = 1)
abstract class MusicPlayerDatabase : RoomDatabase() {
    companion object {
        const val NAME = "music_player_database"
    }

    abstract fun getPlaylistDao(): PlaylistDao

}