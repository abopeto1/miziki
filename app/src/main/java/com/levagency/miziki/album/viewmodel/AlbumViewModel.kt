package com.levagency.miziki.album.viewmodel

import androidx.lifecycle.*
import com.levagency.miziki.album.entity.Album
import com.levagency.miziki.network.MizikiApi
import kotlinx.coroutines.launch
import timber.log.Timber

class AlbumViewModel : ViewModel() {
    private val _navigateToAlbumDetail = MutableLiveData<Long>()

    val navigateToAlbumDetail
        get() = _navigateToAlbumDetail

    // the external immutable LiveData for the response String
    private val _response = MutableLiveData<String>()

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>>
        get() = _albums

//    private lateinit var album: LiveData<Album?>
//    var albums = albumDataRepository.getAllAlbum()

    init {
        initializeAlbums()
    }

    private fun initializeAlbums() {
        getAlbums()
    }

    fun onAlbumTileClicked(albumId: Long){
        _navigateToAlbumDetail.value = albumId
    }

    fun onAlbumTileNavigated() {
        _navigateToAlbumDetail.value = null
    }

    private fun getAlbums(){
        viewModelScope.launch {
            try {
                val list = MizikiApi.retrofitService.getAlbums()
                _albums.value = list
                _response.value = "${list.size} Albums Found"
            } catch (e: Exception) {
                Timber.i(e)
                _response.value = "Failure : ${e.message}"
            }
        }
    }
}