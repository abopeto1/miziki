package com.levagency.miziki.controllers.fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.levagency.miziki.R
import com.levagency.miziki.album.adapter.AlbumAdapter
import com.levagency.miziki.album.viewmodel.AlbumViewModel
import com.levagency.miziki.database.database.MizikiDatabase
import com.levagency.miziki.album.factory.AlbumViewModelFactory
import com.levagency.miziki.album.listener.AlbumListener
import com.levagency.miziki.album.repository.AlbumDataRepository
import com.levagency.miziki.controllers.fragments.ui.*
import com.levagency.miziki.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false)
        // Get Application
        val application = requireNotNull(this.activity).application

        // Initialize Categories
        initHomeCategories(binding)

        // Initialize Recent Played
        initRecentPlayed(application, binding)

//        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        return binding.root
    }

    private fun initHomeCategories(binding: FragmentHomeBinding) {
        val homeAdapter = HomeAdapter()
        val viewModelProvider = HomeViewModelFactory()

        val homeViewModel = ViewModelProvider(this, viewModelProvider).get(HomeViewModel::class.java)
        binding.homeViewModel = homeViewModel

        binding.homeList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = homeAdapter
        }

        homeViewModel.categories.observe(viewLifecycleOwner){
            it.let {
                homeAdapter.addCategories(it)
            }
        }
    }

    private fun initRecentPlayed(application: Application, binding: FragmentHomeBinding){
        // Create an instance of the ViewModel Factory
        val dataSource = MizikiDatabase.getInstance(application).albumDatabaseDao
        val albumDataRepository = AlbumDataRepository(dataSource)
        val viewModelFactory = AlbumViewModelFactory(albumDataRepository)

        // Get a reference to the ViewModel associated with this fragment
        val albumViewModel = ViewModelProvider(this, viewModelFactory).get(AlbumViewModel::class.java)

        binding.lifecycleOwner = this
        binding.albumViewModel = albumViewModel

        val albumAdapter = AlbumAdapter(AlbumListener { albumId ->
            Toast.makeText(context, "$albumId", Toast.LENGTH_LONG).show()
            albumViewModel.onAlbumTileClicked(albumId)
        })

        binding.homeViewModel?.categories?.value?.set(RECENT_PLAYED, HomeCategory("Recent Played", albumAdapter))

        albumViewModel.navigateToAlbumDetail.observe(viewLifecycleOwner) { albumId ->
            albumId?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionMusicFragmentToFavoritesFragment()
                )
                albumViewModel.onAlbumTileNavigated()
            }
        }

        albumViewModel.albums.observe(viewLifecycleOwner) {
            it.let {
                albumAdapter.addHeaderAndSubmitList(it)
            }
        }
    }
}