package com.levagency.miziki.network

import com.levagency.miziki.album.entity.Album
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://my-json-server.typicode.com/abopeto1/mizikidb/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MizikiApiService {
    @GET("albums")
    suspend fun getAlbums(): List<Album>
}

object MizikiApi{
    val retrofitService: MizikiApiService by lazy {
        retrofit.create(MizikiApiService::class.java)
    }
}