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
    @Json(name = "@type") val type: String,
    @Json(name = "hydra:member") val albums: List<NetworkAlbum>?
)

/**
 * Album in a app
 */
@JsonClass(generateAdapter = true)
data class NetworkAlbum(
    @Json(name="id") val id: Long,
    @Json(name="name") val name: String,
    @Json(name="imageUrl") val image: String,
    @Json(name="byArtist") val byArtist: String
)

//fun NetworkAlbumContainer.asDomainModel(): List<Album> {
//    return albums.map {
//        Album(
//            id = it.id,
//            name = it.name,
//            image = it.image,
//            byArtist = it.byArtist
//        )
//    }
//}

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