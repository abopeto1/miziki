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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.levagency.miziki.R
import com.levagency.miziki.domain.album.adapter.AlbumAdapter
import com.levagency.miziki.domain.album.viewmodel.AlbumViewModel
import com.levagency.miziki.domain.album.factory.AlbumViewModelFactory
import com.levagency.miziki.domain.album.listener.AlbumListener
import com.levagency.miziki.controllers.fragments.ui.*
import com.levagency.miziki.databinding.FragmentHomeBinding
import timber.log.Timber

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        // Get Application
        val application = requireNotNull(this.activity).application

        // Initialize Categories
        initHomeCategories(binding)

        // Initialize Recent Played
        initRecentPlayed(binding, application)
        initMakeMondayMoreProductive(binding, application)

        return binding.root
    }

    private fun initHomeCategories(binding: FragmentHomeBinding) {
        val homeAdapter = HomeAdapter()
        val viewModelProvider = HomeViewModelFactory()

        homeViewModel = ViewModelProvider(this, viewModelProvider).get(HomeViewModel::class.java)
        binding.homeViewModel = homeViewModel

        binding.homeList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = homeAdapter
        }
        homeViewModel.categories.value?.let { homeAdapter.addCategories(it) }
        homeViewModel.categories.observe(viewLifecycleOwner, { list ->
            homeAdapter.addCategories(list)
        })
    }

    private fun initRecentPlayed(binding: FragmentHomeBinding, application: Application){
        // Create an instance of the ViewModel Factory
//        val dataSource = MizikiDatabase.getInstance(application).albumDatabaseDao
//        val albumDataRepository = AlbumDataRepository(dataSource)
        val viewModelFactory = AlbumViewModelFactory(app = application)

        // Get a reference to the ViewModel associated with this fragment
        val albumViewModel = ViewModelProvider(this, viewModelFactory).get(AlbumViewModel::class.java)

        binding.albumViewModel = albumViewModel

        val albumAdapter = AlbumAdapter(AlbumListener { albumId ->
            Toast.makeText(context, "$albumId", Toast.LENGTH_LONG).show()
            albumViewModel.onAlbumTileClicked(albumId)
        })

        homeViewModel.recentlyPlayed.value = albumAdapter

        albumViewModel.navigateToAlbumDetail.observe(viewLifecycleOwner, { albumId ->
            albumId?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionMusicFragmentToFavoritesFragment()
                )
                albumViewModel.onAlbumTileNavigated()
            }
        })

        albumViewModel.albums.observe(viewLifecycleOwner, {
            it.let {
                albumAdapter.addHeaderAndSubmitList(it)
                binding.executePendingBindings()
            }
        })
    }

    private fun initMakeMondayMoreProductive(binding: FragmentHomeBinding, application: Application){
        // Create an instance of the ViewModel Factory
//        val dataSource = MizikiDatabase.getInstance(application).albumDatabaseDao
//        val albumDataRepository = AlbumDataRepository(dataSource)
        val viewModelFactory = AlbumViewModelFactory(application)

        // Get a reference to the ViewModel associated with this fragment
        val albumViewModel = ViewModelProvider(this, viewModelFactory).get(AlbumViewModel::class.java)

        binding.albumViewModel = albumViewModel

        val albumAdapter = AlbumAdapter(AlbumListener { albumId ->
            Toast.makeText(context, "$albumId", Toast.LENGTH_LONG).show()
            albumViewModel.onAlbumTileClicked(albumId)
        })

        homeViewModel.makeMondayMoreProductive.value = albumAdapter

        albumViewModel.navigateToAlbumDetail.observe(viewLifecycleOwner, { albumId ->
            albumId?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionMusicFragmentToFavoritesFragment()
                )
                albumViewModel.onAlbumTileNavigated()
            }
        })

        albumViewModel.albums.observe(viewLifecycleOwner, {
            it.let {
                albumAdapter.addHeaderAndSubmitList(it)
                binding.executePendingBindings()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.categories.observe(viewLifecycleOwner, {
            it.let {
                Timber.i(it.toString())
                val homeAdapter = binding.homeList.adapter as HomeAdapter
                homeAdapter.addCategories(it)
            }
        })

        homeViewModel.recentlyPlayed.observe(viewLifecycleOwner, {
            it.let {
                binding.homeViewModel?.categories?.value?.get(RECENT_PLAYED)?.adapter = it
            }
        })

        homeViewModel.makeMondayMoreProductive.observe(viewLifecycleOwner, {
            it.let {
                binding.homeViewModel?.categories?.value?.get(MAKE_MONDAY_MORE_PRODUCTIVE)?.adapter = it
            }
        })

    }
    override fun onDestroy() {
        super.onDestroy()
        Timber.i("Home Fragment Destroyed")
    }
}