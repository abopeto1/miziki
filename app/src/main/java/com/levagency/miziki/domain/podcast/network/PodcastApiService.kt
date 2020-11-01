package com.levagency.miziki.domain.podcast.network

import com.levagency.miziki.domain.podcast.entity.NetworkPodcast
import com.levagency.miziki.network.Result
import com.levagency.miziki.utils.NetworkContainerEntity
import retrofit2.http.GET

interface PodcastApiService {
    @GET("playlists")
    suspend fun getPodcasts(): Result<NetworkContainerEntity<NetworkPodcast>>
}