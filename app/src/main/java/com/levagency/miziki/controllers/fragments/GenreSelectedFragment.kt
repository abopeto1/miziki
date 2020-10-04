package com.levagency.miziki.controllers.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.levagency.miziki.R
import com.levagency.miziki.databinding.FragmentGenreSelectedBinding
import com.levagency.miziki.domain.genre.adapter.GenreSelectedListAdapter
import com.levagency.miziki.domain.genre.view_model.GenreViewModel
import com.levagency.miziki.domain.genre.view_model.GenreViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GenreSelectedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GenreSelectedFragment : Fragment() {
    private val genreViewModel: GenreViewModel by activityViewModels()
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
        binding.genreSelectedList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = GenreSelectedListAdapter()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        genreViewModel.genreSelected.observe(viewLifecycleOwner, {
            genreViewModel.getCategoriesForGenreSelected()
        })
    }
}