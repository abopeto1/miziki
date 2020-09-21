package com.levagency.miziki.controllers.fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.levagency.miziki.R
import com.levagency.miziki.album.adapter.AlbumAdapter
import com.levagency.miziki.album.viewmodel.AlbumViewModel
import com.levagency.miziki.database.database.MizikiDatabase
import com.levagency.miziki.album.factory.AlbumViewModelFactory
import com.levagency.miziki.album.listener.AlbumListener
import com.levagency.miziki.album.repository.AlbumDataRepository
import com.levagency.miziki.controllers.fragments.ui.HomeAdapter
import com.levagency.miziki.controllers.fragments.ui.HomeCategory
import com.levagency.miziki.databinding.FragmentHomeBinding
import timber.log.Timber

class HomeFragment : Fragment() {
    private val categories: MutableList<HomeCategory> = ArrayList()
    private lateinit var homeAdapter: HomeAdapter

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

//        viewModel = ViewModelProvider(this).get(MusicViewModel::class.java)

        return binding.root
    }

    private fun initHomeCategories(binding: FragmentHomeBinding) {
        categories.add(0, HomeCategory("Recent Played", null))

        homeAdapter = HomeAdapter(categories as ArrayList<HomeCategory>)

        binding.homeList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = homeAdapter
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

        categories[0] = HomeCategory("Recent Played", albumAdapter)
        homeAdapter.notifyDataSetChanged()

        albumViewModel.navigateToAlbumDetail.observe(viewLifecycleOwner, { albumId ->
            albumId?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionMusicFragmentToFavoritesFragment()
                )
                albumViewModel.onAlbumTileNavigated()
            }
        })

        albumViewModel.albums.observe(viewLifecycleOwner, {
            it?.let {
                albumAdapter.addHeaderAndSubmitList(it)
            }
        })
    }
}