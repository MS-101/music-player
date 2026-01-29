package com.example.musicplayer.view_models

import android.app.Application
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicplayer.models.Song
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    val exoplayer = ExoPlayer.Builder(application.applicationContext).build()

    val song = mutableStateOf<Song?>(null)
    val songPosition = mutableLongStateOf(0L)
    val isPlaying = mutableStateOf(false)
    val hasPreviousSong = mutableStateOf(false)
    val hasNextSong = mutableStateOf(false)

    init {
        exoplayer.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
                song.value = songs.getOrNull(exoplayer.currentMediaItemIndex)
                updateNavigationButtons()
            }

            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                isPlaying.value = playWhenReady
            }
        })

        viewModelScope.launch {
            while (isActive) {
                songPosition.longValue = exoplayer.currentPosition
                delay(100)
            }
        }
    }

    private fun updateNavigationButtons() {
        hasPreviousSong.value = exoplayer.hasPreviousMediaItem()
        hasNextSong.value = exoplayer.hasNextMediaItem()
    }

    var songs: List<Song> = listOf()
        private set

    fun setSongs(songs: List<Song>, startIndex: Int = 0) {
        this.songs = songs

        val mediaItems = songs.map { song ->
            MediaItem.fromUri(song.uri)
        }

        exoplayer.apply {
            setMediaItems(mediaItems)
            seekTo(startIndex, 0L)
            prepare()
            play()
        }
    }

    override fun onCleared() {
        super.onCleared()
        exoplayer.release()
    }

    fun seekSong(position: Long) = exoplayer.seekTo(position)
    fun pauseSong() = exoplayer.pause()
    fun resumeSong() = exoplayer.play()
    fun playPreviousSong() = exoplayer.seekToPreviousMediaItem()
    fun playNextSong() = exoplayer.seekToNextMediaItem()
}
