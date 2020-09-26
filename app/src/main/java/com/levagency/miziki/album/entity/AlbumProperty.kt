package com.levagency.miziki.album.entity

import com.squareup.moshi.Json

data class AlbumProperty(
    @Json(name = "id") val id: Long,
    @Json(name="name") val name: String,
)