package com.levagency.miziki.domain.recent_played.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.levagency.miziki.database.database.getDatabase
import com.levagency.miziki.domain.recent_played.repository.RecentPlayedRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.lang.IllegalArgumentException

class RecentPlayedViewModel(application: Application): AndroidViewModel(application) {
    private fun getRecentPlayedRepository(application: Application) = RecentPlayedRepository(getDatabase(application))

    val recentPlayed = getRecentPlayedRepository(getApplication()).recentPlayed

    init {
        viewModelScope.launch {
            try {
                getRecentPlayedRepository(getApplication()).refreshAlbums()
            } catch (e: IOException) {
                Timber.e(e)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class RecentPlayedViewModelFactory(val application: Application): ViewModelProvider.Factory{
    /**
     * Creates a new instance of the given `Class`.
     *
     * @param modelClass a `Class` whose instance is requested
     * @param <T>        The type parameter for the ViewModel.
     * @return a newly created ViewModel
    </T> */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecentPlayedViewModel::class.java)) return RecentPlayedViewModel(application) as T

        throw IllegalArgumentException("Unknown Model Class")
    }
}