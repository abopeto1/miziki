package com.levagency.miziki.album.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName="album")
data class Album(
    @PrimaryKey(autoGenerate = true)
    @Json(name = "id")
    var id: Long = 0L,
    @Json(name="name")
    var name: String
)