package com.levagency.miziki.domain.artist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.levagency.miziki.databinding.ArtistTileItemViewBinding
import com.levagency.miziki.databinding.TileItemViewBinding
import com.levagency.miziki.domain.album.entity.Album
import com.levagency.miziki.domain.artist.entity.Artist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistDiffCallback : DiffUtil.ItemCallback<Artist>(){
    override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem == newItem
    }
}

class ArtistAdapter() : ListAdapter<Artist, RecyclerView.ViewHolder>(
    ArtistDiffCallback()
) {
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    class ViewHolder private constructor(private val binding: ArtistTileItemViewBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(
            item: Artist,
        ) {
            binding.artist = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ArtistTileItemViewBinding.inflate(layoutInflater, parent, false)

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

                holder.bind(item)
            }
        }
    }

    fun submit(list: List<Artist>) {
        adapterScope.launch {
            withContext(Dispatchers.Main){
                submitList(list)
            }
        }
    }
}