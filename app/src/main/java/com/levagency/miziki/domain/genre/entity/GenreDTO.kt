package com.levagency.miziki.domain.genre.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Network Genre
 */
@JsonClass(generateAdapter = true)
data class NetworkGenre(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "name") val name: String
)

fun List<NetworkGenre>.asDatabaseModel(): List<DatabaseGenre> {
    return map {
        DatabaseGenre(
            id = it.id,
            name = it.name
        )
    }
}

fun NetworkGenre.asDatabaseModel(): DatabaseGenre {
    return let {
        DatabaseGenre(
            id = it.id,
            name = it.name
        )
    }
}