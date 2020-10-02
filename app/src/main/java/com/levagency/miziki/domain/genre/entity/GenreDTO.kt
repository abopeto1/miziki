package com.levagency.miziki.domain.genre.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Holds a list of genres & moods
 *
 * This is to parse first level of our network result which looks like
 * {
 *  "genres": []
 * }
 */
//@JsonClass(generateAdapter = true)
//data class NetworkGenreContainer(
//    val genres: List<NetworkGenre>
//)

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