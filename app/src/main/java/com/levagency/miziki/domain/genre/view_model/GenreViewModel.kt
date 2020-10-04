package com.levagency.miziki.domain.genre.view_model

import android.app.Application
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.levagency.miziki.database.database.getDatabase
import com.levagency.miziki.R
import com.levagency.miziki.domain.genre.adapter.GenreChild
import com.levagency.miziki.domain.genre.adapter.GenreListAdapter
import com.levagency.miziki.domain.genre.listener.GenreListener
import com.levagency.miziki.domain.genre.repository.GenreRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.lang.IllegalArgumentException

class GenreViewModel(application: Application) : AndroidViewModel(application) {
    // Genre Selected
    val genreSelected = MutableLiveData<Long>()

    private val genreRepository = GenreRepository(getDatabase(application))
    val genreListAdapter = GenreListAdapter(GenreListener { genreId ->
        genreSelected.value = genreId
    })

    // Set genres
    val genres = genreRepository.genres

    val categories: MutableLiveData<List<GenreChild>> by lazy {
        MutableLiveData<List<GenreChild>>()
    }

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

    fun getCategoriesForGenreSelected(): List<GenreChild> {
        Timber.i("get children cat")

        return listOf(
            GenreChild("Popular in these weeks", GenreListAdapter(GenreListener { genreId ->
                genreSelected.value = genreId
            }))
        )
    }
}

@Suppress("UNCHECKED_CAST")
class GenreViewModelFactory(val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GenreViewModel::class.java)) return GenreViewModel(application) as T

        throw IllegalArgumentException("Unknown Viewmodel Class")
    }

}