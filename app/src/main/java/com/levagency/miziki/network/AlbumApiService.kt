package com.levagency.miziki.network

import com.levagency.miziki.domain.album.entity.NetworkAlbumContainer
import retrofit2.http.GET

interface AlbumApiService {
    @GET("albums")
    suspend fun getAlbums(): Result<NetworkAlbumContainer>
}