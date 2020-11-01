package com.levagency.miziki.domain.playlist.entity

/**
 * Playlist represents a playlist model
 */
data class Playlist(
        val id: Long,
        val numTracks: Int?,
        val description: String?,
        val image: String?,
        val name: String,
        val fans: Long? = null
)