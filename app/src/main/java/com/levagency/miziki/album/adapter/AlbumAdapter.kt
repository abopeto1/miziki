package com.levagency.miziki.album.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.levagency.miziki.databinding.TileItemViewBinding
import com.levagency.miziki.album.entity.Album
import com.levagency.miziki.album.listener.AlbumListener

class AlbumDiffCallback : DiffUtil.ItemCallback<Album>(){
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }
}

class AlbumAdapter(private val clickListener: AlbumListener) : ListAdapter<Album, AlbumAdapter.ViewHolder>(AlbumDiffCallback()) {
    class ViewHolder private constructor(private val binding: TileItemViewBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(
            item: Album,
            clickListener: AlbumListener
        ) {
            binding.album = item
            binding.albumTitle.text = item.name
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TileItemViewBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }
}