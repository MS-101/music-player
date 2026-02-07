package com.example.musicplayer.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlaylistEntity::class, PlaylistItemEntity::class], version = 2)
abstract class MusicPlayerDatabase : RoomDatabase() {
    companion object {
        const val NAME = "music_player_database"
    }

    abstract fun getPlaylistDao(): PlaylistDao

}