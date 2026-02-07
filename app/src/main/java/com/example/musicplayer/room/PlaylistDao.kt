package com.example.musicplayer.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface PlaylistDao {
    @Transaction
    @Query("SELECT * FROM playlist")
    fun getAllPlaylists(): LiveData<List<PlaylistWithItemsEntity>>

    @Transaction
    @Query("SELECT * FROM playlist WHERE id = :id")
    fun getPlaylist(id: Long): LiveData<PlaylistWithItemsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlaylist(playlist: PlaylistEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePlaylist(playlist: PlaylistEntity)

    @Delete
    suspend fun deletePlaylist(playlist: PlaylistEntity)

    @Delete
    suspend fun deletePlaylistItem(item: PlaylistItemEntity)
}
