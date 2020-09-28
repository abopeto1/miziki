package com.levagency.miziki.domain.album.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.levagency.miziki.domain.album.viewmodel.AlbumViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class AlbumViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
            return AlbumViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}