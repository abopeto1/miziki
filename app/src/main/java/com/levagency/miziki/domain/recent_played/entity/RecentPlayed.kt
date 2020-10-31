package com.levagency.miziki.domain.recent_played.entity

import com.levagency.miziki.domain.album.entity.Album

data class RecentPlayed(
    val id: Long,
    val type: String,
    val name: String,
    val entityId: Long,
    val imageUrl: String,
)

fun RecentPlayed.asAlbum(): Album {
    return Album(
        id = this.entityId,
        name = this.name,
        image = this.imageUrl,
        byArtist = ""
    )
}