package com.levagency.miziki.domain.album.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.levagency.miziki.database.database.getDatabase
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

    // the external immutable LiveData for the response String
    private val _status = MutableLiveData<AlbumApiStatus>()
//    val status: LiveData<AlbumApiStatus>
//        get() = _status

    val albums = albumRepository.localAlbums
    val albumsOther = albumRepository.albums

//    private lateinit var album: LiveData<DatabaseAlbum?>
//    var albums = albumDataRepository.getAllAlbum()

    init {
        initializeAlbums()
    }

    private fun initializeAlbums() {
        refreshAlbumsFromRepository()
    }

    fun onAlbumTileClicked(albumId: Long){
        _navigateToAlbumDetail.value = albumId
    }

    fun onAlbumTileNavigated() {
        _navigateToAlbumDetail.value = null
    }

    private fun refreshAlbumsFromRepository() =
            viewModelScope.launch {
                try {
                    Timber.i("refresh albums")
                    albumRepository.refreshAlbums()
                    _status.value = AlbumApiStatus.DONE
                } catch (e: IOException) {
                    _status.value = AlbumApiStatus.ERROR
                }
            }
}