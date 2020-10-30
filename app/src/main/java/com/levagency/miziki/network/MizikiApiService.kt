package com.levagency.miziki.network

import com.levagency.miziki.domain.album.entity.NetworkAlbum
import com.levagency.miziki.domain.genre.entity.NetworkGenre
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "http://ec2-3-130-234-231.us-east-2.compute.amazonaws.com/miziki_api/public/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface MizikiApiService {
    @GET("persons")
    suspend fun getPersons(): NetworkPersonContainer

    @GET("genres")
    suspend fun getGenres(): List<NetworkGenre>

    @GET("genres/{id}")
    suspend fun getGenre(@Path("id") id: Long): NetworkGenre
}

object MizikiApi{
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(CallAdapterFactory())
//        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val mizikiNetwork: MizikiApiService = retrofit.create(MizikiApiService::class.java)
    val albumApiService: AlbumApiService = retrofit.create(AlbumApiService::class.java)
    val playlistApiService: PlaylistApiService = retrofit.create(PlaylistApiService::class.java)
    val podcastApiService: PodcastApiService = retrofit.create(PodcastApiService::class.java)
    val recentPlayedApiService: RecentPlayedApiService = retrofit.create(RecentPlayedApiService::class.java)
}