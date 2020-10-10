package com.levagency.miziki.domain.podcast.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Network Podcast
 */
@JsonClass(generateAdapter = true)
data class NetworkPodcast(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "name") val name: String
)

fun List<NetworkPodcast>.asDatabaseModel(): List<DatabasePodcast> {
    return map {
        DatabasePodcast(
            id = it.id,
            name = it.name
        )
    }
}