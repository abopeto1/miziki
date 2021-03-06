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
import com.levagency.miziki.databinding.FragmentGenreSelectedBinding
import com.levagency.miziki.domain.genre.adapter.GenreSelectedListAdapter
import com.levagency.miziki.domain.genre.view_model.GenreViewModel
import com.levagency.miziki.domain.playlist.adapter.PlaylistAdapter
import com.levagency.miziki.domain.playlist.view_model.PlaylistViewModel
import timber.log.Timber

class GenreSelectedFragment : Fragment() {
    private val genreViewModel: GenreViewModel by activityViewModels()
    private val playlistViewModel: PlaylistViewModel by activityViewModels()
    private val genreSelectedListAdapter = GenreSelectedListAdapter()
    private lateinit var binding: FragmentGenreSelectedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (!this::binding.isInitialized) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_genre_selected, container, false)
        }
        
        binding.lifecycleOwner = this

        // Set Title
        binding.title.text = genreViewModel.selected.value?.name

        binding.genreSelectedList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
        binding.genreSelectedList.adapter = genreSelectedListAdapter

//        setListToAdapters()
        return binding.root
    }

//    private fun setListToAdapters(){
//        genreViewModel.popularInWeek.value = PlaylistAdapter(R.id.action_musicFragment_to_playlistFragment)
//
//        genreViewModel.genrePlaylists.value = PlaylistAdapter(R.id.action_musicFragment_to_playlistFragment)
//
//    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genreViewModel.selected.observe(viewLifecycleOwner,{
            binding.title.text = it.name
            genreSelectedListAdapter.addChildren(genreViewModel.getCategoriesForGenreSelected())
        })

        playlistViewModel.playlists.observe(viewLifecycleOwner, {
            Timber.i("Playlist Change")
            genreViewModel.popularInWeek.value?.addPlaylists(it)
            genreViewModel.genrePlaylists.value?.addPlaylists(it)
            binding.executePendingBindings()
        })
    }
}