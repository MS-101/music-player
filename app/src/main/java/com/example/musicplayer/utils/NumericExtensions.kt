package com.example.musicplayer.utils

fun Long.toMmSs(): String {
    val seconds = this / 1000

    val displayedMinutes = seconds / 60
    val displayedSeconds = seconds % 60


    return "%d:%02d".format(displayedMinutes, displayedSeconds)
}