package com.levagency.miziki.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.levagency.miziki.R
import com.levagency.miziki.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        configureBottomMenu()
    }

    private fun configureBottomMenu() {
        NavigationUI.setupWithNavController(binding.bottomNavigation, findNavController(R.id.nav_host_fragment))
    }
}