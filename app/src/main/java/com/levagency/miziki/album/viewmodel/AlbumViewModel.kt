package com.levagency.miziki.album.viewmodel

import androidx.lifecycle.*
import com.levagency.miziki.album.repository.AlbumDataRepository
import kotlinx.coroutines.launch

class AlbumViewModel(
    albumDataRepository: AlbumDataRepository,
) : ViewModel() {
    private val _navigateToAlbumDetail = MutableLiveData<Long>()

    val navigateToAlbumDetail
        get() = _navigateToAlbumDetail


//    private lateinit var album: LiveData<Album?>

    var albums = albumDataRepository.getAllAlbum()

    init {
        initializeAlbums()
    }

    private fun initializeAlbums() {
        viewModelScope.launch {
            return@launch
        }
    }

    fun onAlbumTileClicked(albumId: Long){
        _navigateToAlbumDetail.value = albumId
    }

    fun onAlbumTileNavigated() {
        _navigateToAlbumDetail.value = null
    }
}