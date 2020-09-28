package com.levagency.miziki.domain.album.entity

/**
 * Album represents an album model
 */
data class Album (
    val id: Long = 0L,
    val name: String,
    val image: String,
    val byArtist: Long
)