package com.example.musicplayer.view_models

import android.app.Application
import android.content.ContentUris
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.models.Album
import com.example.musicplayer.models.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumsViewModel(application: Application) : AndroidViewModel(application) {
    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> = _albums

    private val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean) {
            loadAllAlbums()
        }
    }

    init {
        loadAllAlbums()

        application.contentResolver.registerContentObserver(
            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
            true,
            observer
        )
    }

    fun loadAllAlbums() {
        viewModelScope.launch(Dispatchers.IO) {
            val albums = mutableListOf<Album>()
            val queryUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI

            val projection = arrayOf(
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ARTIST
            )

            val sortOrder = "${MediaStore.Audio.Albums.ALBUM} ASC"

            application.contentResolver.query(
                queryUri,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(
                    MediaStore.Audio.Albums._ID
                )
                val albumColumn = cursor.getColumnIndexOrThrow(
                    MediaStore.Audio.Albums.ALBUM
                )
                val artistColumn = cursor.getColumnIndexOrThrow(
                    MediaStore.Audio.Albums.ARTIST
                )

                while(cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val uri = ContentUris.withAppendedId(
                        queryUri, id
                    )

                    val title = cursor.getString(albumColumn)
                    val artist = cursor.getString(artistColumn)

                    val songs = loadAlbumSongs(id)

                    albums.add(
                        Album(id, uri, title, artist, songs)
                    )
                }
            }

            _albums.postValue(albums)
        }
    }

    private fun loadAlbumSongs(albumId: Long): List<Song> {
        val songs = mutableListOf<Song>()
        val queryUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION
        )

        val selection = "${MediaStore.Audio.Media.ALBUM_ID} = ?"
        val selectionArgs = arrayOf(albumId.toString())
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        getApplication<Application>().contentResolver.query(
            queryUri,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            val idCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val durationCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idCol)
                val uri = ContentUris.withAppendedId(queryUri, id)

                val title = cursor.getString(titleCol)
                val artist = cursor.getString(artistCol)
                val duration = cursor.getLong(durationCol)

                songs.add(
                    Song(id, uri, title, artist, duration)
                )
            }
        }

        return songs
    }

    override fun onCleared() {
        super.onCleared()

        application.contentResolver.unregisterContentObserver(observer)
    }
}