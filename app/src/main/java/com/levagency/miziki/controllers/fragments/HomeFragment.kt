package com.levagency.miziki.controllers.fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
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
import com.levagency.miziki.domain.genre.view_model.GenreViewModel
import com.levagency.miziki.domain.playlist.view_model.PlaylistViewModel
import com.levagency.miziki.domain.podcast.view_model.PodcastViewModel
import timber.log.Timber

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
//    private val genreViewModel: GenreViewModel by activityViewModels()
    private val playlistViewModel: PlaylistViewModel by activityViewModels()
    private val albumViewModel: AlbumViewModel by activityViewModels()
//    private val podcastViewModel: PodcastViewModel by activityViewModels()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment if binding is not initialized
        if(!this::binding.isInitialized){
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

            // Initialize Categories
            initHomeCategories()
        }
        Timber.i("Start HomeFragment")
        binding.lifecycleOwner = this
        // Get Application
        val application = requireNotNull(this.activity).application
//        Timber.i(genreViewModel.toString())
        // Initialize Recent Played
//        initRecentPlayed(binding, application)
//        initMakeMondayMoreProductive(binding, application)
        initObservers()
        return binding.root
    }

    private fun initHomeCategories() {
        val homeAdapter = HomeAdapter()
        viewModelFactory = HomeViewModelFactory(this.lifecycle, requireNotNull(this.activity).application)

        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.homeViewModel = homeViewModel

        binding.homeList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = homeAdapter
        }
        homeViewModel.categories.value?.let { homeAdapter.addCategories(it) }
        homeViewModel.categories.observe(viewLifecycleOwner, { list ->
            homeAdapter.addCategories(list)
        })

        homeViewModel.recentlyPlayedData.observe(viewLifecycleOwner, {
            binding.homeViewModel?.recentlyPlayed?.value?.addHeaderAndSubmitList(it)
        })

//        homeViewModel.browser.value = genreViewModel.genreListAdapter
//        genreViewModel.genres.value?.let { homeViewModel.browser.value?.addGenres(it) }

        // set playlist picks adapter
        homeViewModel.playlistPicks.value = playlistViewModel.listAdapter
        playlistViewModel.playlists.value.let {
            if (it != null) {
                homeViewModel.playlistPicks.value?.addPlaylists(it)
            }
        }

//        // set podcasts adapter
//        homeViewModel.podcasts.value = podcastViewModel.listAdapter
//        podcastViewModel.podcasts.value.let {
//            if (it != null) {
//                homeViewModel.podcasts.value?.addPodcasts(it)
//            }
//        }
    }

    private fun initObservers(){
        homeViewModel.showLoadingProgressBar.observe(viewLifecycleOwner, {
            if(it == false){
                binding.loadingSpinner.visibility = View.GONE
            }
        })

//        genreViewModel.genres.observe(viewLifecycleOwner, {
//            homeViewModel.browser.value?.addGenres(it)
//        })

        playlistViewModel.playlists.observe(viewLifecycleOwner, {
            homeViewModel.playlistPicks.value?.addPlaylists(it)
        })
    }
//    private fun initMakeMondayMoreProductive(binding: FragmentHomeBinding, application: Application){
//        // Create an instance of the ViewModel Factory
////        val dataSource = MizikiDatabase.getInstance(application).albumDatabaseDao
////        val albumDataRepository = AlbumDataRepository(dataSource)
//        val viewModelFactory = AlbumViewModelFactory(application)
//
//        // Get a reference to the ViewModel associated with this fragment
//        val albumViewModel = ViewModelProvider(this, viewModelFactory).get(AlbumViewModel::class.java)
//
//        binding.albumViewModel = albumViewModel
//
//        val albumAdapter = AlbumAdapter(AlbumListener { albumId ->
//            Toast.makeText(context, "$albumId", Toast.LENGTH_LONG).show()
//            albumViewModel.onAlbumTileClicked(albumId)
//        })
//
//        homeViewModel.makeMondayMoreProductive.value = albumAdapter
//
//        albumViewModel.navigateToAlbumDetail.observe(viewLifecycleOwner, { albumId ->
//            albumId?.let {
//                this.findNavController().navigate(
//                    HomeFragmentDirections.actionMusicFragmentToFavoritesFragment()
//                )
//                albumViewModel.onAlbumTileNavigated()
//            }
//        })
//
//        albumViewModel.albums.observe(viewLifecycleOwner, {
//            it.let {
//                albumAdapter.addHeaderAndSubmitList(it)
//                binding.executePendingBindings()
//            }
//        })
//    }
//
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.categories.observe(viewLifecycleOwner, {
            it.let {
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
}