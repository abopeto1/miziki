package com.levagency.miziki.network

import com.levagency.miziki.domain.playlist.entity.NetworkPlaylist
import retrofit2.http.GET

interface PlaylistApiService {
    @GET("playlists")
    suspend fun getPlaylists(): List<NetworkPlaylist>
}