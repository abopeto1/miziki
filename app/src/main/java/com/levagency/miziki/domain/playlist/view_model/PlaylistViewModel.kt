package com.levagency.miziki.domain.playlist.view_model

import android.app.Application
import androidx.lifecycle.*
import com.levagency.miziki.database.database.getDatabase
import com.levagency.miziki.domain.playlist.adapter.PlaylistAdapter
import com.levagency.miziki.domain.playlist.repository.PlaylistRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.lang.IllegalArgumentException

class PlaylistViewModel(application: Application) : AndroidViewModel(application) {
    private val playlistRepository = PlaylistRepository(getDatabase(application))

    // Set genres
    val playlists = playlistRepository.playlists

    // list adapter
    var listAdapter = PlaylistAdapter()

    init {
        initPlaylists()
    }

    private fun initPlaylists(){
        viewModelScope.launch {
            try {
                playlistRepository.refreshPlaylist()
            } catch (e: IOException) {
                Timber.e(e)
            }
        }
    }
}


@Suppress("UNCHECKED_CAST")
class PlaylistViewModelFactory(val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaylistViewModel::class.java)) return PlaylistViewModel(application) as T

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}