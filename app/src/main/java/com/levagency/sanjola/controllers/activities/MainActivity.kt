package com.levagency.sanjola.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.levagency.sanjola.R
import com.levagency.sanjola.databinding.ActivityMainBinding
import com.levagency.sanjola.models.Album

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val album: Album = Album(1, "Sublime")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.album = album
    }
}