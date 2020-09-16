package com.levagency.miziki.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.levagency.miziki.R
import com.levagency.miziki.models.Album
import com.levagency.miziki.utils.TileItemViewHolder
import timber.log.Timber

class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {
    var data = listOf<Album>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val imageAlbum: ImageView = itemView.findViewById(R.id.album_image)
        val albumTitle: TextView = itemView.findViewById(R.id.album_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.tile_item_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
//        val res = holder.itemView.context.resources
        holder.albumTitle.text = item.name
    }

    override fun getItemCount() = data.size
}