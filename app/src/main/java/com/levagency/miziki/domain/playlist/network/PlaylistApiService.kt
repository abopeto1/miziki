package com.levagency.miziki.domain.playlist.network

import com.levagency.miziki.domain.playlist.entity.NetworkPlaylist
import com.levagency.miziki.network.Result
import com.levagency.miziki.utils.NetworkContainerEntity
import retrofit2.http.GET

interface PlaylistApiService {
    @GET("playlists")
    suspend fun getPlaylists(): Result<NetworkContainerEntity<NetworkPlaylist>>
}