package com.example.weatherapp.RetrofitNestedApi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CityApiService {
    @GET("v1/city")
    suspend fun fetchCityName(
        @Query("name") city: String,
        @Header("X-Api-Key") apiKey: String
    ): Response<CityResponse>
}