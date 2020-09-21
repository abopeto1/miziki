package com.levagency.miziki.controllers.fragments.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.levagency.miziki.databinding.HomeListItemBinding

//class HomeDiffCallback : DiffUtil.ItemCallback<HomeCategory>(){
//    override fun areItemsTheSame(oldItem: HomeCategory, newItem: HomeCategory): Boolean {
//        return oldItem.title == newItem.title
//    }
//
//    @SuppressLint("DiffUtilEquals")
//    override fun areContentsTheSame(oldItem: HomeCategory, newItem: HomeCategory): Boolean {
//        return oldItem == newItem
//    }
//}

//class HomeItem(val id: Long, val name: String)
class HomeCategory(val title: String, val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?)

class HomeAdapter(
    private val data: ArrayList<HomeCategory>
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    class ViewHolder(private val binding: HomeListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeCategory) {
            binding.homeCategory = item
            binding.title.text = item.title

            // set recycler view
            binding.homeItemList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
            binding.homeItemList.adapter = item.adapter
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}