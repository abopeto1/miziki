package com.levagency.miziki.domain.genre.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.levagency.miziki.R
import com.levagency.miziki.databinding.GenreTileViewBinding
import com.levagency.miziki.domain.genre.entity.Genre
import com.levagency.miziki.domain.genre.listener.GenreListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GenreDiffCallback : DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem == newItem
    }

}

class GenreListAdapter(private val clickListener: GenreListener): ListAdapter<Genre, RecyclerView.ViewHolder>(
    GenreDiffCallback()
) {
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    class ViewHolder private constructor(private val binding: GenreTileViewBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(
            item: Genre,
            clickListener: GenreListener
        ){
            binding.genre = item
            binding.clickListener = clickListener
            binding.itemContainer.apply {
                setOnClickListener(
                    Navigation.createNavigateOnClickListener(R.id.action_genreFragment_to_genreSelectedFragment)
                )
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = GenreTileViewBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> {
                val item = getItem(position)

                holder.bind(item, clickListener)
            }
        }
    }

    fun addGenres(list: List<Genre>){
        adapterScope.launch {
            withContext(Dispatchers.Main) {
                submitList(list)
            }
        }
    }
}