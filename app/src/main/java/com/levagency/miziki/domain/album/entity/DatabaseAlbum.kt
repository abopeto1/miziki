package com.levagency.miziki.domain.album.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * DatabaseAlbum represents an album room entity in the local storage database
 */
@Entity(tableName="album")
data class DatabaseAlbum constructor(
    @PrimaryKey
    val id: Long,
    var name: String,
    var image: String,
    var byArtist: String
)

/**
 * Map DatabaseAlbum to Domain Album Entity
 */
fun List<DatabaseAlbum>.asDomainModel(): List<Album> {
    return map {
        Album(
            id = it.id,
            name = it.name,
            image = it.image,
            byArtist = it.byArtist
        )
    }
}