package com.example.musicplayer.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "playlist")
data class Playlist (
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var name: String
)
