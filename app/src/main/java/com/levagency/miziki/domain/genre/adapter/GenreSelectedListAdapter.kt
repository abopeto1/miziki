package com.levagency.miziki.domain.genre.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.levagency.miziki.databinding.GenreSelectedListItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GenreSelectedDiffCallback: DiffUtil.ItemCallback<GenreChild>() {
    override fun areItemsTheSame(oldItem: GenreChild, newItem: GenreChild): Boolean {
        return oldItem.title == newItem.title
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: GenreChild, newItem: GenreChild): Boolean {
        return oldItem == newItem
    }

}

class GenreChild(
    val title: String,
    var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null,
    var description: String? = null,
    var navigationAction: Int? = null
)

class GenreSelectedListAdapter : ListAdapter<GenreChild, RecyclerView.ViewHolder>(GenreSelectedDiffCallback()) {
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    class ViewHolder(private val binding: GenreSelectedListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: GenreChild){
            binding.genreChild = item

            if (item.navigationAction != null) {
                binding.viewAllButton.apply {
                    visibility = View.VISIBLE
                    setOnClickListener(
                        Navigation.createNavigateOnClickListener(item.navigationAction!!)
                    )
                }

            }
            binding.homeItemList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = item.adapter
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GenreSelectedListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val item = getItem(position)

                holder.bind(item)
            }
        }
    }

    fun addChildren(list: List<GenreChild>){
        adapterScope.launch {
            withContext(Dispatchers.IO){
                submitList(list)
            }
        }
    }
}