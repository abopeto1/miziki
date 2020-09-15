package com.levagency.miziki.controllers.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.levagency.miziki.R
import com.levagency.miziki.controllers.fragments.ui.AlbumViewModel
import com.levagency.miziki.controllers.fragments.ui.MusicViewModel
import com.levagency.miziki.database.database.MizikiDatabase
import com.levagency.miziki.databinding.FragmentMusicBinding
import com.levagency.miziki.models.factories.AlbumViewModelFactory
import timber.log.Timber

class MusicFragment : Fragment() {
    private lateinit var viewModel: MusicViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentMusicBinding>(inflater, R.layout.fragment_music, container, false)
        // Get Application
        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory
        val dataSource = MizikiDatabase.getInstance(application).albumDatabaseDao
        val viewModelFactory = AlbumViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment
        val albumViewModel = ViewModelProvider(this, viewModelFactory).get(AlbumViewModel::class.java)

        Timber.i("Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(MusicViewModel::class.java)
        binding.btnNext.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_musicFragment_to_favoritesFragment)
        }
        binding.lifecycleOwner = this
        binding.albumViewModel = albumViewModel

        return binding.root
    }
}