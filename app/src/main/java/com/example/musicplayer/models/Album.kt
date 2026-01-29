package com.example.musicplayer.models

import android.content.ContentUris
import android.net.Uri
import androidx.core.net.toUri

data class Album (
    val id: Long,
    val uri: Uri,
    val title: String,
    val artist: String,
    val songs: List<Song>,
    val artworkUri: Uri = ContentUris.withAppendedId(
        "content://media/external/audio/albumart".toUri(),
        id
    )
)