package com.levagency.miziki.network

import com.levagency.miziki.domain.album.entity.NetworkAlbum
import retrofit2.http.GET

interface AlbumApiService {
    @GET("albums")
    suspend fun getAlbums(): Result<List<NetworkAlbum>>
}