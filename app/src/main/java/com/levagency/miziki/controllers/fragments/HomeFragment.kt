package com.levagency.miziki.controllers.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.levagency.miziki.R
import com.levagency.miziki.domain.album.viewmodel.AlbumViewModel
import com.levagency.miziki.controllers.fragments.ui.*
import com.levagency.miziki.databinding.FragmentHomeBinding
import com.levagency.miziki.domain.artist.view_model.ArtistViewModel
import com.levagency.miziki.domain.genre.view_model.GenreViewModel
import com.levagency.miziki.domain.playlist.view_model.PlaylistViewModel
import com.levagency.miziki.domain.podcast.view_model.PodcastViewModel
import com.levagency.miziki.domain.recent_played.view_model.RecentPlayedViewModel
import kotlinx.android.synthetic.main.home_list_item.view.*
import timber.log.Timber

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by activityViewModels()

    private val albumViewModel: AlbumViewModel by activityViewModels()
    private val artistViewModel: ArtistViewModel by activityViewModels()
    private val recentPlayedViewModel: RecentPlayedViewModel by activityViewModels()
    private val genreViewModel: GenreViewModel by activityViewModels()
    private val playlistViewModel: PlaylistViewModel by activityViewModels()
    private val podcastViewModel: PodcastViewModel by activityViewModels()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment if binding is not initialized
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        // Initialize Categories
        initHomeCategories()

        binding.lifecycleOwner = this

        initObservers()
        return binding.root
    }

    private fun initHomeCategories() {
        val homeAdapter = HomeAdapter()
        binding.homeViewModel = homeViewModel

        binding.homeList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = homeAdapter
        }
        homeViewModel.categories.value?.let { homeAdapter.addCategories(it) }

        homeViewModel.categories.observe(viewLifecycleOwner, { list ->
            homeAdapter.addCategories(list)
        })

        homeViewModel.recentlyPlayed.value = recentPlayedViewModel.homeAdapter

        homeViewModel.makeMondayMoreProductive.value = playlistViewModel.popularPlaylistAdapter

        homeViewModel.browser.value = genreViewModel.genreListAdapter

        // set playlist picks adapter
        homeViewModel.playlistPicks.value = playlistViewModel.listAdapter

        // set podcasts adapter
        homeViewModel.podcasts.value = podcastViewModel.listAdapter

        // set New Releases
        homeViewModel.newReleases.value = albumViewModel.homeAdapter

        // set Recommends Artists
        homeViewModel.recommendsArtists.value = artistViewModel.homeAdapter

        // set popular playlists
        homeViewModel.popularPlaylists.value = playlistViewModel.popularPlaylistAdapter
    }

    private fun initObservers(){
        homeViewModel.showLoadingProgressBar.observe(viewLifecycleOwner, {
            if(it == false){
                binding.loadingSpinner.visibility = View.GONE
            }
        })

        recentPlayedViewModel.recentPlayed.observe(viewLifecycleOwner, {
            homeViewModel.recentlyPlayed.value?.submit(it)
        })

        albumViewModel.albums.observe(viewLifecycleOwner, {
            homeViewModel.newReleases.value?.addHeaderAndSubmitList(it)
        })

        genreViewModel.genres.observe(viewLifecycleOwner, {
            homeViewModel.browser.value?.addGenres(it)
        })

        recentPlayedViewModel.recentPlayed.observe(viewLifecycleOwner, {
            if(it.isNotEmpty()){
                binding.homeList.list_empty.visibility = View.GONE
            }
            homeViewModel.recentlyPlayed.value?.submit(it)
        })

        podcastViewModel.podcasts.observe(viewLifecycleOwner,{
            homeViewModel.podcasts.value?.addPodcasts(it)
        })

        artistViewModel.recommendsArtist.observe(viewLifecycleOwner, {
            homeViewModel.recommendsArtists.value?.submit(it)
        })

        playlistViewModel.playlists.observe(viewLifecycleOwner, {
            homeViewModel.playlistPicks.value?.addPlaylists(it)
            homeViewModel.popularPlaylists.value?.addPlaylists(it)
            homeViewModel.makeMondayMoreProductive.value?.addPlaylists(it)
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
                homeViewModel.categories.value?.get(RECENT_PLAYED)?.adapter = it
            }
        })
        homeViewModel.makeMondayMoreProductive.observe(viewLifecycleOwner, {
            it.let {
                homeViewModel.categories.value?.get(MAKE_MONDAY_MORE_PRODUCTIVE)?.adapter = it
            }
        })

        homeViewModel.browser.observe(viewLifecycleOwner, {
            it.let {
                homeViewModel.categories.value?.get(BROWSE)?.adapter = it
            }
        })

        homeViewModel.playlistPicks.observe(viewLifecycleOwner, {
            it.let { homeViewModel.categories.value?.get(PLAYLIST_PICKS)?.adapter = it }
        })

        homeViewModel.newReleases.observe(viewLifecycleOwner, {
            it.let {

                binding.homeViewModel?.categories?.value?.get(NEW_RELEASES)?.adapter = it
            }
        })

        homeViewModel.recommendsArtists.observe(viewLifecycleOwner, {
            it.let { binding.homeViewModel?.categories?.value?.get(RECOMMEND_ARTISTS)?.adapter = it }
        })

        homeViewModel.popularPlaylists.observe(viewLifecycleOwner, {
            it.let {
                binding.homeViewModel!!.categories.value?.get(POPULAR_PLAYLISTS)?.adapter = it
                homeViewModel.doneShowingLoadingProgressBar()
            }
        })

        homeViewModel.podcasts.observe(viewLifecycleOwner, {
            it.let { homeViewModel.categories.value?.get(PODCASTS)?.adapter = it }
        })
    }
}