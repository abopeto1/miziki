package com.levagency.sanjola.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.levagency.sanjola.R
import com.levagency.sanjola.databinding.ActivityMainBinding
import com.levagency.sanjola.models.Album
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val album: Album = Album(1, "Sublime")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("OnCreated Call")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.album = album
        configureBottomMenu()
    }

    private fun configureBottomMenu() {
        NavigationUI.setupWithNavController(binding.bottomNavigation, findNavController(R.id.nav_host_fragment))
    }
}