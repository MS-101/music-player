package com.example.musicplayer.models

import android.graphics.Bitmap
import android.net.Uri

data class Song (
    val id: Long,
    val uri: Uri,
    val title: String,
    val artist: String,
    val duration: Long,
    val thumbnail: Bitmap? = null
)