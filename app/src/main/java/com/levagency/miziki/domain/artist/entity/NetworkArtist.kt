package com.levagency.miziki.domain.artist.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkArtist(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "imageUrl") val imageUrl: String?
)

fun List<NetworkArtist>.asDatabaseModel(): List<DatabaseArtist> {
    return map {
        DatabaseArtist(
            id = it.id,
            name = it.name,
            imageUrl = it.imageUrl
        )
    }
}