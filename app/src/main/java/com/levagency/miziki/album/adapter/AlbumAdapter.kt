package com.levagency.miziki.album.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.levagency.miziki.R
import com.levagency.miziki.databinding.TileItemViewBinding
import com.levagency.miziki.album.entity.Album
import com.levagency.miziki.album.listener.AlbumListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class AlbumDiffCallback : DiffUtil.ItemCallback<DataItem>(){
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem {
    abstract val id: Long

    data class AlbumItem(val album: Album) : DataItem() {
        override val id = album.id
    }

    object Header : DataItem() {
        override val id = Long.MIN_VALUE
    }
}

class AlbumAdapter(private val clickListener: AlbumListener) : ListAdapter<DataItem, RecyclerView.ViewHolder>(AlbumDiffCallback()) {
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_header, parent, false)

                return HeaderViewHolder(view)
            }
        }
    }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val albumItem = getItem(position) as DataItem.AlbumItem
                holder.bind(albumItem.album, clickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.AlbumItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    fun addHeaderAndSubmitList(list: List<Album>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.AlbumItem(it)}
            }

            withContext(Dispatchers.Main){
                submitList(items)
            }
        }
    }
}