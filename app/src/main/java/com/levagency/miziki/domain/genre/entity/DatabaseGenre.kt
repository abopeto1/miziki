package com.levagency.miziki.domain.genre.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * DatabaseGenre represents an genre room entity in the local storage database
 */
@Entity(tableName = "genre")
data class DatabaseGenre constructor(
    @PrimaryKey val id: Long,
    val name: String
)

/**
 * Map DatabaseGenre to Domain Genre Entity
 */
fun List<DatabaseGenre>.asDomainModel(): List<Genre> {
    return map {
        Genre(
            id = it.id,
            name = it.name
        )
    }
}

/**
 * Convert DatabaseGenre to Domain Genre Entity
 */
fun DatabaseGenre.asDomainModel(): Genre {
    let {
        return Genre(
            id = it.id,
            name = it.name
        )
    }
}