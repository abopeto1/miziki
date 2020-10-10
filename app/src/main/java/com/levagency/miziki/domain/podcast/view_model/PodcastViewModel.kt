package com.levagency.miziki.domain.podcast.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.levagency.miziki.database.database.getDatabase
import com.levagency.miziki.domain.podcast.adapter.PodcastAdapter
import com.levagency.miziki.domain.podcast.repository.PodcastRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.lang.IllegalArgumentException

class PodcastViewModel(application: Application): AndroidViewModel(application) {
    val repository = PodcastRepository(getDatabase(application))

    // Set Podcasts
    val podcasts = repository.podcasts

    // List Adapter
    val listAdapter = PodcastAdapter()

    init {
        initPodcasts()
    }

    fun initPodcasts(){
        viewModelScope.launch {
            try {
                repository.refreshPodcasts()
            } catch (e: IOException) {
                Timber.i(e)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class PodcastViewModelFactory(val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PodcastViewModel::class.java)) return PodcastViewModel(application) as T

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}
