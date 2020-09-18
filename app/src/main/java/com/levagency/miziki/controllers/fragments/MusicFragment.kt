package com.levagency.miziki.controllers.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.levagency.miziki.R
import com.levagency.miziki.album.adapter.AlbumAdapter
import com.levagency.miziki.controllers.fragments.ui.AlbumViewModel
import com.levagency.miziki.controllers.fragments.ui.MusicViewModel
import com.levagency.miziki.database.database.MizikiDatabase
import com.levagency.miziki.databinding.FragmentMusicBinding
import com.levagency.miziki.album.factory.AlbumViewModelFactory
import com.levagency.miziki.album.listener.AlbumListener
import com.levagency.miziki.album.repository.AlbumDataRepository

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

        val albumAdapter = AlbumAdapter(AlbumListener { albumId -> Toast.makeText(context, "$albumId", Toast.LENGTH_LONG).show() })
        val manager = GridLayoutManager(activity, 3, GridLayoutManager.HORIZONTAL, false)
        binding.albumHorizontalList.apply {
            adapter = albumAdapter
            layoutManager = manager
        }

        albumViewModel.albums.observe(viewLifecycleOwner, {
            it?.let {
                albumAdapter.submitList(it)
            }
        })

        return binding.root
    }
}