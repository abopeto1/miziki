package com.levagency.miziki.domain.artist.network

import com.levagency.miziki.domain.artist.entity.NetworkArtistContainer
import com.levagency.miziki.network.Result
import retrofit2.http.GET

interface ArtistApiService {
    @GET("artists")
    suspend fun getArtists(): Result<NetworkArtistContainer>
}