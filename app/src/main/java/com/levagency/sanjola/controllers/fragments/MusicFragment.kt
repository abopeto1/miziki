package com.levagency.sanjola.controllers.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.levagency.sanjola.R
import com.levagency.sanjola.databinding.FragmentMusicBinding

class MusicFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentMusicBinding>(inflater, R.layout.fragment_music, container, false)

        return binding.root
    }
}