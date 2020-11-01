package com.levagency.miziki.domain.album.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.levagency.miziki.database.database.getDatabase
import com.levagency.miziki.domain.album.adapter.AlbumAdapter
import com.levagency.miziki.domain.album.repository.AlbumDataRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

enum class AlbumApiStatus { LOADING, ERROR, DONE }

class AlbumViewModel(application: Application) : AndroidViewModel(application) {
    private val albumRepository = AlbumDataRepository(getDatabase(application))
    private val _navigateToAlbumDetail = MutableLiveData<Long>()

    val navigateToAlbumDetail
        get() = _navigateToAlbumDetail

    val albums = albumRepository.albums

    val homeAdapter = AlbumAdapter()

    init {
        initializeAlbums()
    }

    private fun initializeAlbums() {
        viewModelScope.launch {
            try {
                albumRepository.refreshAlbums()
            } catch (e: IOException) {
                Timber.i(e)
            }
        }
    }

    fun onAlbumTileClicked(albumId: Long){
        _navigateToAlbumDetail.value = albumId
    }

    fun onAlbumTileNavigated() {
        _navigateToAlbumDetail.value = null
    }
}