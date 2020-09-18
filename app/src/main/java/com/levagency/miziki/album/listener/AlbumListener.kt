package com.levagency.miziki.album.listener

import com.levagency.miziki.album.entity.Album

class AlbumListener(
    val clickListener: (albumId: Long) -> Unit
) {
    fun onClick(album: Album) = clickListener(album.id)
}