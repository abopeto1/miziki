package com.levagency.miziki.utils

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.levagency.miziki.R
import com.levagency.miziki.domain.album.viewmodel.AlbumApiStatus

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String?){
    url?.let {
        val imgUri = url.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imageView)
    }
}

@BindingAdapter("albumApiStatus")
fun bindStatus(statusImageView: ImageView, status: AlbumApiStatus){
    when (status) {
        AlbumApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        AlbumApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_broken_image)
        }
        AlbumApiStatus.DONE -> {
            statusImageView.visibility = View.VISIBLE
        }
    }
}