package com.levagency.miziki.domain.album.listener

import com.levagency.miziki.domain.album.entity.Album

class AlbumListener(
    val clickListener: (albumId: Long) -> Unit
) {
    fun onClick(album: Album) = clickListener(album.id)
}