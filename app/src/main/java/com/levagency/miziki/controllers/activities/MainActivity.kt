package com.levagency.miziki.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.levagency.miziki.R
import com.levagency.miziki.databinding.ActivityMainBinding
import com.levagency.miziki.domain.genre.view_model.GenreViewModel
import com.levagency.miziki.domain.genre.view_model.GenreViewModelFactory
import com.levagency.miziki.domain.playlist.view_model.PlaylistViewModel
import com.levagency.miziki.domain.playlist.view_model.PlaylistViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var genreViewModel: GenreViewModel
    lateinit var playlistViewModel: PlaylistViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initGenreViewModel()
        initPlaylistViewModel()
        configureBottomMenu()
    }

    private fun configureBottomMenu() {
        NavigationUI.setupWithNavController(binding.bottomNavigation, findNavController(R.id.nav_host_fragment))
    }

    private fun initGenreViewModel() {
        val genreViewModelFactory = GenreViewModelFactory(this.application)

        genreViewModel = ViewModelProvider(this, genreViewModelFactory).get(GenreViewModel::class.java)
    }

    private fun initPlaylistViewModel() {
        val playlistViewModelFactory = PlaylistViewModelFactory(this.application)

        playlistViewModel = ViewModelProvider(this, playlistViewModelFactory).get(PlaylistViewModel::class.java)
    }
}