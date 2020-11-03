package com.levagency.miziki.domain.playlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.levagency.miziki.R
import com.levagency.miziki.databinding.PlaylistTileItemViewBinding
import com.levagency.miziki.domain.playlist.entity.Playlist
import com.levagency.miziki.utils.EntityListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaylistDiffUtil: DiffUtil.ItemCallback<Playlist>() {
    override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
        return oldItem == newItem
    }
}

class PlaylistAdapter(val navigateId: Int, val entityListener: EntityListener<Playlist>) : ListAdapter<Playlist, RecyclerView.ViewHolder>(PlaylistDiffUtil()){
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    class ViewHolder(private val binding: PlaylistTileItemViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(
            item: Playlist,
            navigateId: Int,
            entityListener: EntityListener<Playlist>
        ){
            binding.playlist = item
            binding.playlistImage.setOnClickListener {
                entityListener.clickListener(item)
                Navigation.findNavController(it).navigate(navigateId)
            }
            binding.playButton.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_musicFragment_to_playerFragment)
            }
            if(item.fans == null){
                binding.favoriteIcon.visibility = View.GONE
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PlaylistTileItemViewBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ViewHolder -> {
                val item = getItem(position)

                return holder.bind(item, navigateId, entityListener)
            }
        }
    }

    fun addPlaylists(list: List<Playlist>){
        adapterScope.launch {
            withContext(Dispatchers.Main){
                submitList(list)
            }
        }
    }
}