package com.example.tourismchat1.network

import TourismApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8002/" // Make sure this is correct
    val api: TourismApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TourismApiService::class.java)
    }
}