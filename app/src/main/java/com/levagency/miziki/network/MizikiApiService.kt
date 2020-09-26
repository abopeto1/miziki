package com.levagency.miziki.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://my-json-server.typicode.com/abopeto1/mizikidb/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface MizikiApiService {
    @GET("albums")
    fun getAlbums():
            Call<String>
}

object MizikiApi{
    val retrofitService: MizikiApiService by lazy {
        retrofit.create(MizikiApiService::class.java)
    }
}