package com.levagency.miziki.domain.genre.view_model

import android.app.Application
import androidx.lifecycle.*
import com.levagency.miziki.R
import com.levagency.miziki.database.database.getDatabase
import com.levagency.miziki.domain.genre.adapter.GenreChild
import com.levagency.miziki.domain.genre.adapter.GenreListAdapter
import com.levagency.miziki.domain.genre.entity.Genre
import com.levagency.miziki.domain.genre.listener.GenreListener
import com.levagency.miziki.domain.genre.repository.GenreRepository
import com.levagency.miziki.domain.playlist.adapter.PlaylistAdapter
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.lang.IllegalArgumentException

class GenreViewModel(application: Application) : AndroidViewModel(application) {
    private val genreRepository = GenreRepository(getDatabase(application))

    // Genre Selected
    val selected = MutableLiveData<Genre>()

    var genreListAdapter = GenreListAdapter(GenreListener { genre ->
        selected.value = genre
    }, R.id.action_musicFragment_to_genreSelectedFragment)

    // Set genres
    val genres = genreRepository.genres

    val popularInWeek = MutableLiveData<PlaylistAdapter>()

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
            GenreChild("Popular in these weeks", popularInWeek.value)
        )
    }

    private fun selectGenre(item: Genre){
        Timber.i(item.toString())
        selected.value = item
    }
}

@Suppress("UNCHECKED_CAST")
class GenreViewModelFactory(val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GenreViewModel::class.java)) return GenreViewModel(application) as T

        throw IllegalArgumentException("Unknown Viewmodel Class")
    }

}