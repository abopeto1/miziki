package com.levagency.miziki.controllers.fragments.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.levagency.miziki.domain.album.adapter.AlbumAdapter

const val RECENT_PLAYED = 0
const val MAKE_MONDAY_MORE_PRODUCTIVE = 1
const val BROWSE = 2
const val PLAYLIST_PICKS = 3
const val PODCASTS = 4
const val NEW_RELEASES = 5
const val RECOMMEND = 6
const val POPULAR_ARTISTS = 7

class HomeViewModel : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
            get() = _loading
    val categories = MutableLiveData<MutableList<HomeCategory>>()
    val recentlyPlayed = MutableLiveData<AlbumAdapter>()
    val makeMondayMoreProductive = MutableLiveData<AlbumAdapter>()

    init {
        initializeCategories()
    }

    private fun initializeCategories() {
        categories.value = ArrayList()

        // Init Recent Played
        categories.value?.add(RECENT_PLAYED, HomeCategory("Recently Played", recentlyPlayed.value))

        // Init Make Monday more productive
        categories.value?.add(MAKE_MONDAY_MORE_PRODUCTIVE, HomeCategory("Make Monday More Productive", makeMondayMoreProductive.value))

        // Init Browser
        categories.value?.add(BROWSE, HomeCategory("Browse", makeMondayMoreProductive.value))

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

        _loading.value = false
    }

    fun onSetCategory(type: Int, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>){
        categories.value?.get(type)?.adapter = adapter
    }
}