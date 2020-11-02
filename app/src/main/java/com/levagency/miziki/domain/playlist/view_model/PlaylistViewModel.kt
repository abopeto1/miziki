package com.levagency.miziki.domain.playlist.view_model

import android.app.Application
import androidx.lifecycle.*
import com.levagency.miziki.R
import com.levagency.miziki.database.database.getDatabase
import com.levagency.miziki.domain.playlist.adapter.PlaylistAdapter
import com.levagency.miziki.domain.playlist.entity.Playlist
import com.levagency.miziki.domain.playlist.repository.PlaylistRepository
import com.levagency.miziki.utils.EntityListener
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.lang.IllegalArgumentException

class PlaylistViewModel(application: Application) : AndroidViewModel(application) {
    private val playlistRepository = PlaylistRepository(getDatabase(application))

    // Set Playlists
    val playlists = playlistRepository.playlists

    val selectedPlaylist = MutableLiveData<Playlist>()

    // list adapter
    var listAdapter = PlaylistAdapter(R.id.action_musicFragment_to_playlistFragment,EntityListener<Playlist> {
            it -> getPlaylist(it)
    })

    var popularPlaylistAdapter = PlaylistAdapter(R.id.action_musicFragment_to_playlistFragment, EntityListener<Playlist> {
        it -> getPlaylist(it)
    })

    init {
        initPlaylists()
    }

    private fun getPlaylist(playlist: Playlist){
        selectedPlaylist.value = playlist
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