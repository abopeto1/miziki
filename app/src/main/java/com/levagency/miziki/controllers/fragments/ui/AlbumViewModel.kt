package com.levagency.miziki.controllers.fragments.ui

import androidx.lifecycle.*
import com.levagency.miziki.album.entity.Album
import com.levagency.miziki.album.repository.AlbumDataRepository
import kotlinx.coroutines.launch

class AlbumViewModel(
    albumDataRepository: AlbumDataRepository,
) : ViewModel() {
    private lateinit var album: LiveData<Album?>

    var albums = albumDataRepository.getAllAlbum()

    init {
        initializeAlbums()
    }

    private fun initializeAlbums() {
        viewModelScope.launch {
            return@launch
        }
    }
}