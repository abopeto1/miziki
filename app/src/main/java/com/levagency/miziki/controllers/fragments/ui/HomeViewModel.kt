package com.levagency.miziki.controllers.fragments.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.levagency.miziki.album.adapter.AlbumAdapter

const val RECENT_PLAYED = 0

class HomeViewModel : ViewModel() {
    val categories = MutableLiveData<MutableList<HomeCategory>>()
    val recentlyPlayed = MutableLiveData<AlbumAdapter>()

    init {
        initializeCategories()
    }

    private fun initializeCategories() {
        categories.value = ArrayList()

        // Init Recent Played
        categories.value?.add(RECENT_PLAYED, HomeCategory("Recently Played", recentlyPlayed.value))
    }

    fun onSetCategory(type: Int, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>){
        categories.value?.get(type)?.adapter = adapter
    }
}