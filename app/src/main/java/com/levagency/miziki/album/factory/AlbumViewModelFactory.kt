package com.levagency.miziki.album.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.levagency.miziki.album.viewmodel.AlbumViewModel
import com.levagency.miziki.album.repository.AlbumDataRepository
import java.lang.IllegalArgumentException

class AlbumViewModelFactory(
    private val dataSource: AlbumDataRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
            return AlbumViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}