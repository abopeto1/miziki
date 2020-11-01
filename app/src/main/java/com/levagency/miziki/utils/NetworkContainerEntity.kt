package com.levagency.miziki.utils

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkContainerEntity<T>(
    @Json(name = "hydra:member") val data: List<T>
)