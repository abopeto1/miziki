package com.levagency.sanjola.controllers.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.levagency.sanjola.R
import com.levagency.sanjola.controllers.fragments.ui.MusicViewModel
import com.levagency.sanjola.databinding.FragmentMusicBinding
import timber.log.Timber

class MusicFragment : Fragment() {
    private lateinit var viewModel: MusicViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentMusicBinding>(inflater, R.layout.fragment_music, container, false)

        Timber.i("Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(MusicViewModel::class.java)
        binding.btnNext.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_musicFragment_to_favoritesFragment)
        }
        return binding.root
    }
}