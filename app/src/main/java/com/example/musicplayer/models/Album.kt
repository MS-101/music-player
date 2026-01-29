package com.example.musicplayer.models

import android.net.Uri
import coil3.Bitmap

data class Album (
    val id: Long,
    val uri: Uri,
    val title: String,
    val artist: String,
    val songs: List<Song>,
    val thumbnail: Bitmap? = null
)