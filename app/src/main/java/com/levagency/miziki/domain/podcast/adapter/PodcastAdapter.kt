package com.levagency.miziki.domain.podcast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.levagency.miziki.databinding.PodcastTileViewBinding
import com.levagency.miziki.domain.podcast.entity.Podcast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PodcastDiffUtil: DiffUtil.ItemCallback<Podcast>() {
    override fun areItemsTheSame(oldItem: Podcast, newItem: Podcast): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Podcast, newItem: Podcast): Boolean {
        return oldItem == newItem
    }
}

class PodcastAdapter: ListAdapter<Podcast, RecyclerView.ViewHolder>(PodcastDiffUtil()){
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    class ViewHolder(private val binding: PodcastTileViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(
                item: Podcast
        ){
            binding.podcast = item
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PodcastTileViewBinding.inflate(layoutInflater, parent, false)

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

                return holder.bind(item)
            }
        }
    }

    fun addPodcasts(list: List<Podcast>){
        adapterScope.launch {
            withContext(Dispatchers.Main){
                submitList(list)
            }
        }
    }
}