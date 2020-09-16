package com.levagency.miziki.controllers.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.levagency.miziki.R
import com.levagency.miziki.adapters.AlbumAdapter
import com.levagency.miziki.controllers.fragments.ui.AlbumViewModel
import com.levagency.miziki.controllers.fragments.ui.MusicViewModel
import com.levagency.miziki.database.database.MizikiDatabase
import com.levagency.miziki.databinding.FragmentMusicBinding
import com.levagency.miziki.models.Album
import com.levagency.miziki.models.factories.AlbumViewModelFactory
import com.levagency.miziki.repositories.AlbumDataRepository

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
        val albumDataRepository = AlbumDataRepository(dataSource)
        val viewModelFactory = AlbumViewModelFactory(albumDataRepository)

        // Get a reference to the ViewModel associated with this fragment
        val albumViewModel = ViewModelProvider(this, viewModelFactory).get(AlbumViewModel::class.java)

        viewModel = ViewModelProvider(this).get(MusicViewModel::class.java)

        binding.lifecycleOwner = this
        binding.albumViewModel = albumViewModel

        val albumAdapter = AlbumAdapter()

        binding.albumHorizontalList.apply {
            adapter = albumAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        albumViewModel.albums.observe(viewLifecycleOwner, {
            it?.let {
                albumAdapter.data = it
            }
        })
        loadOneAlbum(albumAdapter)
        return binding.root
    }

    private fun loadOneAlbum(adapter: AlbumAdapter){
        val d = ArrayList<Album>()

        d.add(Album(89, name = "Mer"))
        adapter.data = d
    }
}