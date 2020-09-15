package com.levagency.miziki.models.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.levagency.miziki.controllers.fragments.ui.AlbumViewModel
import com.levagency.miziki.database.dao.AlbumDatabaseDao
import java.lang.IllegalArgumentException

class AlbumViewModelFactory(
    private val dataSource: AlbumDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
            return AlbumViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}