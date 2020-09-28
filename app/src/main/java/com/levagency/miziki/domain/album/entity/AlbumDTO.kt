package com.levagency.miziki.domain.album.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Holds a list of albums
 *
 * This is to parse first level of our network result which looks like
 * {
 *  "albums": []
 * }
 */
@JsonClass(generateAdapter = true)
data class NetworkAlbumContainer(
    val albums: List<NetworkAlbum>
)

/**
 * Album in a app
 */
@JsonClass(generateAdapter = true)
data class NetworkAlbum(
    @field:Json(name="id") val id: Long,
    @field:Json(name="name") val name: String,
    @field:Json(name="image") val image: String,
    @field:Json(name="byArtist") val byArtist: Long
)

fun NetworkAlbumContainer.asDomainModel(): List<Album> {
    return albums.map {
        Album(
            id = it.id,
            name = it.name,
            image = it.image,
            byArtist = it.byArtist
        )
    }
}

fun List<NetworkAlbum>.asDatabaseModel(): List<DatabaseAlbum> {
    return map {
        DatabaseAlbum(
            id = it.id,
            name = it.name,
            image = it.image,
            byArtist = it.byArtist
        )
    }
}