package com.levagency.miziki.controllers.activities.view_model

import android.app.Application
import androidx.lifecycle.*
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import java.lang.IllegalArgumentException

class PlayerViewModel(application: Application): AndroidViewModel(application), LifecycleObserver {
    private val _player = MutableLiveData<Player?>()
    val player: LiveData<Player?>
        get() = _player

    init {
        // Alternatively expose this as a dependency
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        setupPlayer()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        releasePlayer()
    }

    private fun setupPlayer() {
        val player = SimpleExoPlayer.Builder(getApplication()).build()
        this._player.value = player
    }

    private fun releasePlayer() {
        _player.value?.release()
    }

    override fun onCleared() {
        super.onCleared()
        releasePlayer()

        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
    }
}

@Suppress("UNCHECKED_CAST")
class PlayerViewModelFactory(val application: Application): ViewModelProvider.Factory {
    /**
     * Creates a new instance of the given `Class`.
     *
     *
     *
     * @param modelClass a `Class` whose instance is requested
     * @param <T>        The type parameter for the ViewModel.
     * @return a newly created ViewModel
    </T> */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayerViewModel::class.java)) return PlayerViewModel(application) as T

        throw IllegalArgumentException("Unknown Class Provide")
    }

}