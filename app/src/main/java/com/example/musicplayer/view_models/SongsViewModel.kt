package com.example.musicplayer.view_models

import android.app.Application
import android.content.ContentUris
import android.database.ContentObserver
import android.graphics.Bitmap
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import android.util.Size
import com.example.musicplayer.models.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.Q)
class SongsViewModel(application: Application) : AndroidViewModel(application) {
    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> = _songs

    private val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean) {
            loadAllSongs()
        }
    }

    init {
        loadAllSongs()

        application.contentResolver.registerContentObserver(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            true,
            observer
        )
    }

    fun loadAllSongs() {
        viewModelScope.launch(Dispatchers.IO) {
            val songs = mutableListOf<Song>()
            val queryUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID,
            )

            val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
            val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

            application.contentResolver.query(
                queryUri,
                projection,
                selection,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(
                    MediaStore.Audio.Media._ID
                )
                val titleColumn = cursor.getColumnIndexOrThrow(
                    MediaStore.Audio.Media.TITLE
                )
                val artistColumn = cursor.getColumnIndexOrThrow(
                    MediaStore.Audio.Media.ARTIST
                )
                val durationColumn = cursor.getColumnIndexOrThrow(
                    MediaStore.Audio.Media.DURATION
                )
                val albumIdColumn = cursor.getColumnIndexOrThrow(
                    MediaStore.Audio.Media.ALBUM_ID
                )

                while(cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val uri = ContentUris.withAppendedId(
                        queryUri, id
                    )

                    val title = cursor.getString(titleColumn)
                    val artist = cursor.getString(artistColumn)
                    val duration = cursor.getLong(durationColumn)
                    val albumId = cursor.getLong(albumIdColumn)

                    val thumbnailUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        albumId
                    )
                    val thumbnail: Bitmap? = try {
                        application.contentResolver.loadThumbnail(
                            thumbnailUri, Size(5100, 500), null
                        )
                    } catch (_: Exception) {
                        null
                    }

                    songs.add(Song(id, uri, title, artist, duration, thumbnail))
                }
            }

            _songs.postValue(songs)
        }
    }

    override fun onCleared() {
        super.onCleared()

        application.contentResolver.unregisterContentObserver(observer)
    }
}