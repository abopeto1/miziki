package com.levagency.miziki.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util
import com.levagency.miziki.R
import com.levagency.miziki.databinding.ActivityMainBinding
import com.levagency.miziki.domain.album.factory.AlbumViewModelFactory
import com.levagency.miziki.domain.album.viewmodel.AlbumViewModel
import com.levagency.miziki.domain.genre.view_model.GenreViewModel
import com.levagency.miziki.domain.genre.view_model.GenreViewModelFactory
import com.levagency.miziki.domain.playlist.view_model.PlaylistViewModel
import com.levagency.miziki.domain.playlist.view_model.PlaylistViewModelFactory
import com.levagency.miziki.domain.podcast.view_model.PodcastViewModel
import com.levagency.miziki.domain.podcast.view_model.PodcastViewModelFactory
import com.levagency.miziki.domain.recent_played.view_model.RecentPlayedViewModel
import com.levagency.miziki.domain.recent_played.view_model.RecentPlayedViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var albumViewModel: AlbumViewModel
    private lateinit var genreViewModel: GenreViewModel
    private lateinit var playlistViewModel: PlaylistViewModel
    private lateinit var podcastViewModel: PodcastViewModel
    private lateinit var recentPlayedViewModel: RecentPlayedViewModel // Set recent played view model

    // Player Variables
    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT < 24){
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if((Util.SDK_INT < 24 || player == null)){
            initializePlayer()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Initialize all view models
        initViewModels()

        configureBottomMenu()
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24){
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT < 24){
            releasePlayer()
        }
    }

    private fun initializePlayer(){
        player = SimpleExoPlayer.Builder(this).build()
        binding.minPlayer.player = player

        val mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp3))
        player!!.setMediaItem(mediaItem)

        val secondMediaItem = MediaItem.fromUri(getString(R.string.media_url_mp3))
        player!!.addMediaItem(secondMediaItem)

        player!!.playWhenReady = playWhenReady
        player!!.seekTo(currentWindow, playbackPosition)
        player!!.prepare()
        player!!.pause()
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player!!.release()
            player = null
        }
    }

    private fun configureBottomMenu() {
        NavigationUI.setupWithNavController(binding.bottomNavigation, findNavController(R.id.nav_host_fragment))
    }

    private fun initViewModels(){
//        initPlaylistViewModel()
        initAlbumViewModel() // Initialize Album View Model
        initRecentPlayedViewModel() // Initialize Recent Played View Model
    }

    private fun initGenreViewModel() {
        val genreViewModelFactory = GenreViewModelFactory(this.application)

        genreViewModel = ViewModelProvider(this, genreViewModelFactory).get(GenreViewModel::class.java)
    }

    private fun initPlaylistViewModel() {
        val playlistViewModelFactory = PlaylistViewModelFactory(this.application)

        playlistViewModel = ViewModelProvider(this, playlistViewModelFactory).get(PlaylistViewModel::class.java)
    }

    private fun initPodcastViewModel() {
        // Podcasts
        val podcastViewModelFactory = PodcastViewModelFactory(this.application)

        podcastViewModel = ViewModelProvider(this, podcastViewModelFactory).get(PodcastViewModel::class.java)
    }

    // Initialize Album View Model
    private fun initAlbumViewModel() {
        val albumViewModelFactory = AlbumViewModelFactory(this.application)

        albumViewModel = ViewModelProvider(this, albumViewModelFactory).get(AlbumViewModel::class.java)
    }

    // Initialize Recent Played View Model
    private fun initRecentPlayedViewModel() {
        val recentPlaylistViewModelFactory = RecentPlayedViewModelFactory(this.application)

        recentPlayedViewModel = ViewModelProvider(this, recentPlaylistViewModelFactory).get(RecentPlayedViewModel::class.java)
    }
}