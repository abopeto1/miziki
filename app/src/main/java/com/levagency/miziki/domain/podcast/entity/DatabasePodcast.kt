package com.levagency.miziki.domain.podcast.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * DatabasePodcast represents a podcast room entity in the local storage database
 */
@Entity(tableName = "podcast")
data class DatabasePodcast constructor(
    @PrimaryKey val id: Long,
    val name: String
)

/**
 * Map DatabasePlaylist to Domain Playlist Entity
 */
fun List<DatabasePodcast>.asDomainModel(): List<Podcast> {
    return map {
        Podcast(
            id = it.id,
            name = it.name
        )
    }
}