package com.levagency.miziki.domain.genre.view_model

import android.app.Application
import androidx.lifecycle.*
import com.levagency.miziki.database.database.getDatabase
import com.levagency.miziki.domain.genre.adapter.GenreListAdapter
import com.levagency.miziki.domain.genre.entity.Genre
import com.levagency.miziki.domain.genre.repository.GenreRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.lang.IllegalArgumentException

class GenreViewModel(application: Application) : AndroidViewModel(application) {
    private val genreRepository = GenreRepository(getDatabase(application))
    val genreListAdapter = GenreListAdapter()

    // Set genres
    val genres = genreRepository.genres

    init {
        initGenre()
    }

    private fun initGenre(){
        viewModelScope.launch {
            try {
                genreRepository.refreshGenre()
            } catch (e: IOException) {
                Timber.e(e)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class GenreViewModelFactory(val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GenreViewModel::class.java)) return GenreViewModel(application) as T

        throw IllegalArgumentException("Unknown Viewmodel Class")
    }

}