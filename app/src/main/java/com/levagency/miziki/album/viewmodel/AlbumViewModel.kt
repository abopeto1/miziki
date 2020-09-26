package com.levagency.miziki.album.viewmodel

import androidx.lifecycle.*
import com.levagency.miziki.album.entity.Album
import com.levagency.miziki.album.repository.AlbumDataRepository
import com.levagency.miziki.network.MizikiApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class AlbumViewModel(
    albumDataRepository: AlbumDataRepository,
) : ViewModel() {
    private val _navigateToAlbumDetail = MutableLiveData<Long>()

    val navigateToAlbumDetail
        get() = _navigateToAlbumDetail

    // the external immutable LiveData for the response String
    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

//    private lateinit var album: LiveData<Album?>

    var albums = albumDataRepository.getAllAlbum()

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
        MizikiApi.retrofitService.getAlbums().enqueue(
            object: Callback<List<Album>>{
                override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                    _response.value = "Failure: "+ t.message
                    Timber.i(t.message)
                }

                override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                    _response.value = "${response.body()?.size} Albums Found"
                    Timber.i(_response.value)
                }

            }
        )
    }
}