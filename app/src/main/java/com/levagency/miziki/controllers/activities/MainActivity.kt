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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var genreViewModel: GenreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initGenreViewModel()

        configureBottomMenu()
    }

    private fun configureBottomMenu() {
        NavigationUI.setupWithNavController(binding.bottomNavigation, findNavController(R.id.nav_host_fragment))
    }

    fun initGenreViewModel() {
        val genreViewModelFactory = GenreViewModelFactory(this.application)

        genreViewModel = ViewModelProvider(this, genreViewModelFactory).get(GenreViewModel::class.java)
    }
}