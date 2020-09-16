package com.levagency.miziki.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.levagency.miziki.R
import com.levagency.miziki.models.Album
import com.levagency.miziki.utils.TileItemViewHolder
import timber.log.Timber

class AlbumAdapter : RecyclerView.Adapter<TileItemViewHolder>() {
    var data = listOf<Album>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.tile_item_view, parent, false) as TextView

        return TileItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TileItemViewHolder, position: Int) {
        val item = data[position]

        holder.textView.text = item.name
    }

    override fun getItemCount() = data.size
}