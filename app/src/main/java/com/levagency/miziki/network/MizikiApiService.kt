package com.levagency.miziki.network

import com.levagency.miziki.domain.album.entity.NetworkAlbum
import com.levagency.miziki.domain.album.entity.NetworkAlbumContainer
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://my-json-server.typicode.com/abopeto1/mizikidb/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface MizikiApiService {
    @GET("albums")
    suspend fun getAlbums(): List<NetworkAlbum>

    @GET("persons")
    suspend fun getPersons(): NetworkPersonContainer
}

object MizikiApi{
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val mizikiNetwork: MizikiApiService = retrofit.create(MizikiApiService::class.java)
}