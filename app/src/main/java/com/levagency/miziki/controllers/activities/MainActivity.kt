package com.levagency.miziki.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util
import com.levagency.miziki.R
import com.levagency.miziki.controllers.activities.view_model.PlayerViewModel
import com.levagency.miziki.controllers.activities.view_model.PlayerViewModelFactory
import com.levagency.miziki.controllers.fragments.ui.HomeViewModel
import com.levagency.miziki.controllers.fragments.ui.HomeViewModelFactory
import com.levagency.miziki.databinding.ActivityMainBinding
import com.levagency.miziki.domain.album.factory.AlbumViewModelFactory
import com.levagency.miziki.domain.album.viewmodel.AlbumViewModel
import com.levagency.miziki.domain.artist.view_model.ArtistViewModel
import com.levagency.miziki.domain.artist.view_model.ArtistViewModelFactory
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
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var albumViewModel: AlbumViewModel // set Album ViewModel
    private lateinit var artistViewModel: ArtistViewModel // set Artist ViewModel
    private lateinit var genreViewModel: GenreViewModel // set Genre ViewModel
    private lateinit var playlistViewModel: PlaylistViewModel // set Playlist View Model
    private lateinit var podcastViewModel: PodcastViewModel
    private lateinit var recentPlayedViewModel: RecentPlayedViewModel // Set recent played view model
    private lateinit var playerViewModel: PlayerViewModel // Player View Model

    // Player Variables
//    private var player: SimpleExoPlayer? = null
//    private var playWhenReady = true
//    private var currentWindow = 0
//    private var playbackPosition: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Initialize all view models
        initViewModels()

        configureBottomMenu()
    }

//    private fun initializePlayer(){
//        player = SimpleExoPlayer.Builder(this).build()
//        binding.minPlayer.player = player
//
//        val mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp3))
//        player!!.setMediaItem(mediaItem)
//
//        val secondMediaItem = MediaItem.fromUri(getString(R.string.media_url_mp3))
//        player!!.addMediaItem(secondMediaItem)
//
//        player!!.playWhenReady = playWhenReady
//        player!!.seekTo(currentWindow, playbackPosition)
//        player!!.prepare()
//        player!!.pause()
//    }

//    private fun releasePlayer() {
//        if (player != null) {
//            playWhenReady = player!!.playWhenReady
//            playbackPosition = player!!.currentPosition
//            currentWindow = player!!.currentWindowIndex
//            player!!.release()
//            player = null
//        }
//    }

    private fun showBottomView() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    private fun hideBottomView() {
        binding.bottomNavigation.visibility = View.GONE
    }

    private fun configureBottomMenu() {
        NavigationUI.setupWithNavController(binding.bottomNavigation, findNavController(R.id.nav_host_fragment))
        val navController = findNavController(R.id.nav_host_fragment)

        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when(destination.id){
                R.id.musicFragment -> showBottomView()
                R.id.favoritesFragment -> showBottomView()
                R.id.playerFragment -> {
                    hideBottomView()
                }
                else -> hideBottomView()
            }
        }
    }

    private fun initViewModels(){
        initHomeViewModel() // Initialize Home View Model
        initAlbumViewModel() // Initialize Album View Model
        initArtistViewModel() // Initialize Artist View Model
        initGenreViewModel()
        initPlaylistViewModel() // Initialize Playlist View Model
        initPodcastViewModel() // Podcasts
        initRecentPlayedViewModel() // Initialize Recent Played View Model
        initPlayerViewModel()
    }

    private fun initPlayerViewModel(){
        val playerViewModelFactory = PlayerViewModelFactory(this.application)

        playerViewModel = ViewModelProvider(this, playerViewModelFactory).get(PlayerViewModel::class.java)
    }

    private fun initHomeViewModel(){
        val homeViewModelFactory = HomeViewModelFactory(this.application)

        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
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

    // Initialize Artist View Model
    private fun initArtistViewModel() {
        val artistViewModelFactory = ArtistViewModelFactory(this.application)

        artistViewModel = ViewModelProvider(this, artistViewModelFactory).get(ArtistViewModel::class.java)
    }
}