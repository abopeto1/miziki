package com.levagency.miziki.album.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.levagency.miziki.album.viewmodel.AlbumViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class AlbumViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
            return AlbumViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}