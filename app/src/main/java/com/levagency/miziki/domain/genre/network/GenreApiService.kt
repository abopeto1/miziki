package com.levagency.miziki.domain.genre.network

import com.levagency.miziki.domain.genre.entity.NetworkGenre
import com.levagency.miziki.network.Result
import com.levagency.miziki.utils.NetworkContainerEntity
import retrofit2.http.GET

interface GenreApiService {
    @GET("genres")
    suspend fun getGenres(): Result<NetworkContainerEntity<NetworkGenre>>
}