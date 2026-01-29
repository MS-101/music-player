package com.example.musicplayer.view_models

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

data class PlayerState(
    val song: Song?,
    val songPosition: Long,
    val isPlaying: Boolean,
    val hasPreviousSong: Boolean,
    val hasNextSong: Boolean,
    val shuffleModeEnabled: Boolean,
    val repeatMode: Int
)

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    val exoplayer = ExoPlayer.Builder(application.applicationContext).build()

    val song = mutableStateOf<Song?>(null)
    val songPosition = mutableLongStateOf(0L)
    val isPlaying = mutableStateOf(false)
    val hasPreviousSong = mutableStateOf(false)
    val hasNextSong = mutableStateOf(false)
    val shuffleModeEnabled = mutableStateOf(false)
    val repeatMode = mutableIntStateOf(0)

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

            override fun onShuffleModeEnabledChanged(shuffleModeEnabledChanged: Boolean) {
                shuffleModeEnabled.value = shuffleModeEnabledChanged
            }

            override fun onRepeatModeChanged(repeatModeChanged: Int) {
                super.onRepeatModeChanged(repeatModeChanged)

                repeatMode.intValue = repeatModeChanged
                updateNavigationButtons()
            }
        })

        viewModelScope.launch {
            while (isActive) {
                songPosition.longValue = exoplayer.currentPosition
                delay(100)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        exoplayer.release()
    }

    private fun updateNavigationButtons() {
        hasPreviousSong.value = exoplayer.hasPreviousMediaItem()
        hasNextSong.value = exoplayer.hasNextMediaItem()
    }

    var songs: List<Song> = listOf()
        private set

    fun getState(): PlayerState {
        val song by song
        val isPlaying by isPlaying
        val songPosition by songPosition
        val hasPreviousSong by hasPreviousSong
        val hasNextSong by hasNextSong
        val shuffleModeEnabled by shuffleModeEnabled
        val repeatMode by repeatMode

        return PlayerState(
            song,
            songPosition,
            isPlaying,
            hasPreviousSong,
            hasNextSong,
            shuffleModeEnabled,
            repeatMode
        )
    }

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



    fun seekSong(position: Long) = exoplayer.seekTo(position)
    fun pauseSong() = exoplayer.pause()
    fun resumeSong() = exoplayer.play()
    fun playPreviousSong() = exoplayer.seekToPreviousMediaItem()
    fun playNextSong() = exoplayer.seekToNextMediaItem()
    fun setShuffleMode(shuffleModeEnabled: Boolean) {
        exoplayer.shuffleModeEnabled = shuffleModeEnabled
    }
    fun setRepeatMode(repeatMode: Int) {
        exoplayer.repeatMode = repeatMode
    }
}
