package com.example.musicplayer.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "playlist")
data class PlaylistEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)

@Entity(tableName = "playlist_item", primaryKeys = ["playlistId", "songId"])
data class PlaylistItemEntity(
    val playlistId: Long,
    val songId: Long
)

data class PlaylistWithItemsEntity(
    @Embedded
    val playlist: PlaylistEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "playlistId"
    )
    val items: List<PlaylistItemEntity>
)
