package com.levagency.miziki.domain.artist.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkArtistContainer(
    @Json(name = "hydra:member") val artists: List<NetworkArtist>
)