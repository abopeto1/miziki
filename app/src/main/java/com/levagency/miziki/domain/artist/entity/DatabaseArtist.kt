package com.levagency.miziki.domain.artist.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artist")
data class DatabaseArtist(
    @PrimaryKey val id: Long,
    val name: String,
    val imageUrl: String?
)

fun List<DatabaseArtist>.asDomainModel(): List<Artist> {
    return map {
        Artist(
            id = it.id,
            name = it.name,
            imageUrl = it.imageUrl
        )
    }
}