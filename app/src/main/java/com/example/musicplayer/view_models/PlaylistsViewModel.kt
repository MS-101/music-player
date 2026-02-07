package com.example.musicplayer.view_models

import android.app.Application
import android.content.ContentUris
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.MainApplication
import com.example.musicplayer.models.Playlist
import com.example.musicplayer.models.Song
import com.example.musicplayer.room.PlaylistEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.Q)
class PlaylistsViewModel(application: Application) : AndroidViewModel(application) {
    private val playlistDao = MainApplication.db.getPlaylistDao()

    fun getAllPlaylists(): LiveData<List<Playlist>> {
        return playlistDao.getAllPlaylists().switchMap { playlistEntities ->
            val result = MutableLiveData<List<Playlist>>()

            viewModelScope.launch(Dispatchers.IO) {
                val allItems = playlistEntities.flatMap { it.items }
                val songIds = allItems.map { it.songId }.distinct()
                val songMap = fetchSongs(songIds)

                val missingItems = allItems.filter { it.songId !in songMap.keys }
                if (missingItems.isNotEmpty()) {
                    missingItems.forEach { playlistDao.deletePlaylistItem(it) }
                }

                val playlists = playlistEntities.map { playlistEntity ->
                    Playlist(
                        id = playlistEntity.playlist.id,
                        name = playlistEntity.playlist.name,
                        songs = playlistEntity.items.mapNotNull { songMap[it.songId] }
                    )
                }
                result.postValue(playlists)
            }

            result
        }
    }

    fun getPlaylist(id: Long): LiveData<Playlist> {
        return playlistDao.getPlaylist(id).switchMap { playlistEntity ->
            val result = MutableLiveData<Playlist>()

            viewModelScope.launch(Dispatchers.IO) {
                val songIds = playlistEntity.items.map { it.songId }
                val songMap = fetchSongs(songIds)

                val missingItems = playlistEntity.items.filter { it.songId !in songMap.keys }
                if (missingItems.isNotEmpty()) {
                    missingItems.forEach { playlistDao.deletePlaylistItem(it) }
                }

                result.postValue(
                    Playlist(
                        id = playlistEntity.playlist.id,
                        name = playlistEntity.playlist.name,
                        songs = playlistEntity.items.mapNotNull { songMap[it.songId] }
                    )
                )
            }

            result
        }
    }

    private fun fetchSongs(songIds: List<Long>): Map<Long, Song> {
        if (songIds.isEmpty()) return emptyMap()
        val songs = mutableMapOf<Long, Song>()
        val queryUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID,
        )

        songIds.chunked(100).forEach { chunk ->
            val selection = "${MediaStore.Audio.Media._ID} IN (${chunk.joinToString(",")})"
            getApplication<Application>().contentResolver.query(
                queryUri,
                projection,
                selection,
                null,
                null
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val uri = ContentUris.withAppendedId(queryUri, id)
                    val title = cursor.getString(titleColumn)
                    val artist = cursor.getString(artistColumn)
                    val duration = cursor.getLong(durationColumn)
                    val albumId = cursor.getLong(albumIdColumn)

                    val thumbnailUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        albumId
                    )
                    val thumbnail: Bitmap? = try {
                        getApplication<Application>().contentResolver.loadThumbnail(
                            thumbnailUri, Size(512, 512), null
                        )
                    } catch (_: Exception) {
                        null
                    }

                    songs[id] = Song(id, uri, title, artist, duration, thumbnail)
                }
            }
        }
        return songs
    }

    fun addPlaylist(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistDao.addPlaylist(PlaylistEntity(0, name))
        }
    }

    fun updatePlaylist(id: Long, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistDao.updatePlaylist(PlaylistEntity(id, name))
        }
    }

    fun deletePlaylist(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistDao.deletePlaylist(PlaylistEntity(id, ""))
        }
    }
}
