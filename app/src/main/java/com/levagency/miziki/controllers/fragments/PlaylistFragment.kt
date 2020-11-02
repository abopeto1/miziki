package com.levagency.miziki.controllers.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.levagency.miziki.R
import com.levagency.miziki.databinding.FragmentPlaylistBinding
import com.levagency.miziki.domain.playlist.view_model.PlaylistViewModel


class PlaylistFragment : Fragment() {
    private val playlistViewModel: PlaylistViewModel by activityViewModels()
    private lateinit var binding: FragmentPlaylistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_playlist,container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
    }

    private fun initObservers(){
        playlistViewModel.selectedPlaylist.observe(viewLifecycleOwner, {
            binding.playlist = it
        })
    }
}