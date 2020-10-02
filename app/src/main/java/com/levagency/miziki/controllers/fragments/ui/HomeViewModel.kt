package com.levagency.miziki.controllers.fragments.ui

import android.app.Application
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.levagency.miziki.R
import com.levagency.miziki.database.database.getDatabase
import com.levagency.miziki.domain.album.adapter.AlbumAdapter
import com.levagency.miziki.domain.album.listener.AlbumListener
import com.levagency.miziki.domain.album.repository.AlbumDataRepository
import com.levagency.miziki.domain.genre.adapter.GenreListAdapter
import com.levagency.miziki.domain.genre.entity.Genre
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

const val RECENT_PLAYED = 0
const val MAKE_MONDAY_MORE_PRODUCTIVE = 1
const val BROWSE = 2
const val PLAYLIST_PICKS = 3
const val PODCASTS = 4
const val NEW_RELEASES = 5
const val RECOMMEND = 6
const val POPULAR_ARTISTS = 7

class HomeViewModel(lifecycle: Lifecycle, application: Application) : ViewModel(), LifecycleObserver {
    private val albumRepository = AlbumDataRepository(getDatabase(application))

    val categories = MutableLiveData<MutableList<HomeCategory>>()

    // status for Loading
    private var _showLoadingProgressBar = MutableLiveData<Boolean>()
    val showLoadingProgressBar: LiveData<Boolean>
        get() = _showLoadingProgressBar

    val recentlyPlayed = MutableLiveData<AlbumAdapter>()
    val recentlyPlayedData = albumRepository.localAlbums
    val makeMondayMoreProductive = MutableLiveData<AlbumAdapter>()

    val browser = MutableLiveData<GenreListAdapter>()

    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun initializeCategories() {
        categories.value = ArrayList()

        // Init Recent Played
        initRecentPlayed()
        categories.value?.add(RECENT_PLAYED, HomeCategory("Recently Played", recentlyPlayed.value))

        // Init Make Monday more productive
        categories.value?.add(MAKE_MONDAY_MORE_PRODUCTIVE, HomeCategory("Make Monday More Productive", makeMondayMoreProductive.value))

        // Init Browser
        categories.value?.add(BROWSE, HomeCategory("Browse", browser.value))

        // Init Playlist Picks
        categories.value?.add(PLAYLIST_PICKS, HomeCategory("Playlist Picks", makeMondayMoreProductive.value))

        // Init Podcasts
        categories.value?.add(PODCASTS, HomeCategory("Podcasts", makeMondayMoreProductive.value))

        // Init New Releases For You
        categories.value?.add(NEW_RELEASES, HomeCategory("New Releases for You", makeMondayMoreProductive.value))

        // Init New Releases For You
        categories.value?.add(NEW_RELEASES, HomeCategory("New Releases for You", makeMondayMoreProductive.value))

        // Init New Releases For You
        categories.value?.add(RECOMMEND, HomeCategory("You might like these Artists", makeMondayMoreProductive.value))

        // Init New Releases For You
        categories.value?.add(POPULAR_ARTISTS, HomeCategory("Popular Artists", makeMondayMoreProductive.value))

        doneShowingLoadingProgressBar()
    }

    private fun initRecentPlayed() =
        viewModelScope.launch {
            try {
                val albumAdapter = AlbumAdapter(AlbumListener {
//            Toast.makeText(context, "$albumId", Toast.LENGTH_LONG).show()
//            albumViewModel.onAlbumTileClicked(albumId)
                    Navigation.createNavigateOnClickListener(R.id.action_musicFragment_to_favoritesFragment, null)
                })
                recentlyPlayed.value = albumAdapter

                albumRepository.refreshAlbums()
                recentlyPlayedData.value?.let { recentlyPlayed.value?.addHeaderAndSubmitList(it) }
            } catch (e: IOException){
                Timber.i(e)
            }
        }
//    fun onSetCategory(type: Int, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>){
//        categories.value?.get(type)?.adapter = adapter
//    }

    private fun doneShowingLoadingProgressBar(){
        _showLoadingProgressBar.value = false
    }
}