package com.levagency.miziki.domain.playlist.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * DatabasePlaylist represents a playlist room entity in the local storage database
 */
@Entity(tableName = "playlist")
data class DatabasePlaylist constructor(
    @PrimaryKey val id: Long,
    val numTracks: Int,
    val description: String,
    val image: String,
    val name: String
)

/**
 * Map DatabasePlaylist to Domain Playlist Entity
 */
fun List<DatabasePlaylist>.asDomainModel(): List<Playlist> {
    return map {
        Playlist(
            id = it.id,
            numTracks = it.numTracks,
            description = it.description,
            image = it.image,
            name = it.name
        )
    }
}