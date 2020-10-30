package com.levagency.miziki.network

import com.levagency.miziki.domain.recent_played.entity.NetworkRecentPlayedContainer
import retrofit2.http.GET

interface RecentPlayedApiService {
    @GET("user_listen_histories")
    suspend fun getRecentPlayed(): Result<NetworkRecentPlayedContainer>
}