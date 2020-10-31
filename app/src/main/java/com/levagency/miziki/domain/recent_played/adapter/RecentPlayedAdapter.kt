package com.levagency.miziki.domain.recent_played.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.levagency.miziki.databinding.PodcastTileViewBinding
import com.levagency.miziki.databinding.TileItemViewBinding
import com.levagency.miziki.domain.recent_played.entity.RecentPlayed
import com.levagency.miziki.domain.recent_played.entity.asAlbum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class RecentPlayedDiffCallback : DiffUtil.ItemCallback<RecentPlayed>(){
    override fun areItemsTheSame(oldItem: RecentPlayed, newItem: RecentPlayed): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecentPlayed, newItem: RecentPlayed): Boolean {
        return oldItem == newItem
    }
}

class RecentPlayedAdapter: ListAdapter<RecentPlayed, RecyclerView.ViewHolder>(RecentPlayedDiffCallback()) {
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    class AlbumViewHolder private constructor(
        private val binding: TileItemViewBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentPlayed){
            binding.album = item.asAlbum()
            binding.albumTitle.text = item.name
        }

        companion object {
            fun from(parent: ViewGroup): AlbumViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TileItemViewBinding.inflate(layoutInflater, parent, false)

                return AlbumViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)

        return when(item.type) {
            "Album" -> 0
            "Artist" -> 1
            else -> throw IllegalArgumentException("Unknown type of recent played")
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> AlbumViewHolder.from(parent)
            else -> throw IllegalArgumentException("Unknown ViewType of recent played")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    fun submit(list: List<RecentPlayed>){
        adapterScope.launch {
            withContext(Dispatchers.Main){
                submitList(list)
            }
        }
    }
}