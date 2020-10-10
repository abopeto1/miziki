package com.levagency.miziki.network

import com.levagency.miziki.domain.podcast.entity.NetworkPodcast
import retrofit2.http.GET

interface PodcastApiService {
    @GET("playlists")
    suspend fun getPodcasts(): List<NetworkPodcast>
}