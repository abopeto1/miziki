package com.levagency.miziki.controllers.fragments.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber

const val RECENT_PLAYED = 0

class HomeViewModel : ViewModel() {
    val categories = MutableLiveData<MutableList<HomeCategory>>()

    init {
        viewModelScope.launch {
            initializeCategories()
        }
    }

    private fun initializeCategories() {
        categories.value = ArrayList()

        // Init Recent Played
        categories.value?.add(RECENT_PLAYED, HomeCategory("Recent Played", null))
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("HomeViewModel Destroyed")
    }
}