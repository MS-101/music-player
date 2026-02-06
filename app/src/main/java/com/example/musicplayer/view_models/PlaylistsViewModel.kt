package com.example.musicplayer.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.MainApplication
import com.example.musicplayer.models.Playlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistsViewModel(application: Application) : AndroidViewModel(application) {
    val playlistDao = MainApplication.db.getPlaylistDao()

    val playlists: LiveData<List<Playlist>> = playlistDao.getAllPlaylists()

    fun addPlaylist(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistDao.addPlaylist(Playlist(0, title))
        }
    }

    fun updatePlaylist(playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistDao.updatePlaylist(playlist)
        }
    }

    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistDao.deletePlaylist(playlist)
        }
    }
}
