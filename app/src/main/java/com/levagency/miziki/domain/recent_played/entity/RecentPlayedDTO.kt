package com.levagency.miziki.domain.recent_played.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkRecentPlayedContainer(
    @Json(name = "hydra:member") val recentPlayed: List<NetworkRecentPlayed>
)

@JsonClass(generateAdapter = true)
data class NetworkRecentPlayed(
    @Json(name = "type") val type: String,
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "entityId") val entityId: Long,
    @Json(name = "imageUrl") val imageUrl: String
)

@Entity(tableName="recent_played")
data class DatabaseRecentPlayed(
    @PrimaryKey val id: Long,
    val type: String,
    val name: String,
    val entityId: Long,
    val imageUrl: String
)

fun List<NetworkRecentPlayed>.asDatabaseModel(): List<DatabaseRecentPlayed> {
    return map {
        DatabaseRecentPlayed(
            id = it.id,
            type = it.type,
            name = it.name,
            entityId = it.entityId,
            imageUrl = it.imageUrl
        )
    }
}

fun List<DatabaseRecentPlayed>.asDomainModel(): List<RecentPlayed> {
    return map {
        RecentPlayed(
            id = it.id,
            type = it.type,
            name = it.name,
            entityId = it.entityId,
            imageUrl = it.imageUrl
        )
    }
}