package com.levagency.miziki.domain.playlist.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Network Playlist
 */
@JsonClass(generateAdapter = true)
data class NetworkPlaylist(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "numTracks") val numTracks: Int,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "image") val image: String,
    @field:Json(name = "name") val name: String
)

//fun List<NetworkPlaylist>.asDatabaseModel(): List<DatabasePlaylist> {
//    return map {
//        DatabasePlaylist(
//            id = it.id,
//            numTracks = it.numTracks,
//            description = it.description,
//            image = it.image,
//            name = it.name
//        )
//    }
//}