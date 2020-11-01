package com.levagency.miziki.network

import com.levagency.miziki.domain.artist.network.ArtistApiService
import com.levagency.miziki.domain.genre.network.GenreApiService
import com.levagency.miziki.domain.playlist.network.PlaylistApiService
import com.levagency.miziki.domain.podcast.network.PodcastApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "http://ec2-3-130-234-231.us-east-2.compute.amazonaws.com/miziki_api/public/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object MizikiApi{
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(CallAdapterFactory())
//        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val albumApiService: AlbumApiService = retrofit.create(AlbumApiService::class.java)
    val artistApiService: ArtistApiService = retrofit.create(ArtistApiService::class.java)
    val genreApiService: GenreApiService = retrofit.create(GenreApiService::class.java)
    val playlistApiService: PlaylistApiService = retrofit.create(PlaylistApiService::class.java)
    val podcastApiService: PodcastApiService = retrofit.create(PodcastApiService::class.java)
    val recentPlayedApiService: RecentPlayedApiService = retrofit.create(RecentPlayedApiService::class.java)
}