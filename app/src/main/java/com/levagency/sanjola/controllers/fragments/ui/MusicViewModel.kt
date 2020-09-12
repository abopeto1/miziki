package com.levagency.sanjola.controllers.fragments.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.levagency.sanjola.models.Album
import timber.log.Timber

class MusicViewModel : ViewModel() {
    var albums = MutableLiveData<MutableList<Album>>()

    fun setAlbums(){
        albums.value?.add(Album(1, "Sublime"))
        albums.value?.add(Album(1, "Mystère du voile"))
    }

    init {
        albums.value = ArrayList<Album>()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("MusicViewModel Destroyed")
    }
}