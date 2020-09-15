package com.levagency.miziki.controllers.fragments.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.levagency.miziki.database.dao.AlbumDatabaseDao
import com.levagency.miziki.models.Album
import kotlinx.coroutines.launch

class AlbumViewModel(
    private val database: AlbumDatabaseDao,
    application: Application
) : AndroidViewModel(application ) {
    private var album = MutableLiveData<Album?>()

    private fun initializeAlbums() {
        viewModelScope.launch {
            album.value = getAlbumFromDatabase()
        }
    }

    private suspend fun getAlbumFromDatabase(): Album? {
        return database.getAlbum()

    }

    private suspend fun insert(album: Album) {
        database.insert(album)
    }

    fun onGetAlbums() {
        viewModelScope.launch {
            val newAlbum = Album(name = "Sublime")
            insert(newAlbum)
        }
    }

    init {
        initializeAlbums()
    }
}