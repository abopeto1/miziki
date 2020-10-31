package com.levagency.miziki.controllers.fragments.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.levagency.miziki.databinding.HomeListItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeDiffCallback : DiffUtil.ItemCallback<HomeCategory>(){
    override fun areItemsTheSame(oldItem: HomeCategory, newItem: HomeCategory): Boolean {
        return oldItem.title == newItem.title
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: HomeCategory, newItem: HomeCategory): Boolean {
        return oldItem == newItem
    }
}

class HomeCategory(
    val title: String,
    var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null,
    var description: String? = null,
    var navigationAction: Int? = null,
    var notFoundText: String = "Not Found"
)

class HomeAdapter : ListAdapter<HomeCategory, RecyclerView.ViewHolder>(HomeDiffCallback()) {
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    class ViewHolder(private val binding: HomeListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeCategory) {
            binding.homeCategory = item
            binding.title.text = item.title

            if(item.navigationAction != null){
                binding.viewAllButton.visibility = View.VISIBLE
                binding.viewAllButton.setOnClickListener(
                    Navigation.createNavigateOnClickListener(item.navigationAction!!)
                )
            }
            // set recycler view
            binding.homeItemList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = item.adapter
            }
            binding.listEmpty.text = item.notFoundText
//            binding.homeItemList.adapter = item.adapter
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HomeListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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

    fun addCategories(list: List<HomeCategory>){
        adapterScope.launch {
            withContext(Dispatchers.Main){
                submitList(list)
            }
        }
    }
}