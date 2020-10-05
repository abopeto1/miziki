package com.levagency.miziki.controllers.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.levagency.miziki.R
import com.levagency.miziki.databinding.FragmentGenreBinding
import com.levagency.miziki.domain.genre.view_model.GenreViewModel
import com.levagency.miziki.domain.genre.view_model.GenreViewModelFactory

class GenreFragment : Fragment() {
    private lateinit var binding: FragmentGenreBinding
    private lateinit var genreViewModel: GenreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!this::genreViewModel.isInitialized){
            val genreViewModelFactory = GenreViewModelFactory(requireNotNull(this.activity).application)

            genreViewModel = ViewModelProvider(this, genreViewModelFactory).get(GenreViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment if binding is not initialized
        if(!this::binding.isInitialized){
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_genre, container, false)
        }

        // set adapter
        binding.genreList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = genreViewModel.genreListAdapter
        }

        // set observers
        initObservers()

        return binding.root
    }

    private fun initObservers(){
        genreViewModel.genres.observe(viewLifecycleOwner, {
            genreViewModel.genreListAdapter.addGenres(it)
        })
    }
}