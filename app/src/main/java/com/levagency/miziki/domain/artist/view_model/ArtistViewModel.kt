package com.levagency.miziki.domain.artist.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.levagency.miziki.database.database.getDatabase
import com.levagency.miziki.domain.artist.adapter.ArtistAdapter
import com.levagency.miziki.domain.artist.repository.ArtistRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.lang.IllegalArgumentException

class ArtistViewModel(application: Application): AndroidViewModel(application) {
    val artistRepository = ArtistRepository(getDatabase(application))

    val recommendsArtist = artistRepository.artists

    val homeAdapter = ArtistAdapter()

    init{
        viewModelScope.launch {
            try {
                artistRepository.refreshArtists()
            } catch (e: IOException){
                Timber.e(e)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ArtistViewModelFactory(val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ArtistViewModel::class.java)) return ArtistViewModel(application) as T
        throw IllegalArgumentException("Unknown Model Class")
    }

}