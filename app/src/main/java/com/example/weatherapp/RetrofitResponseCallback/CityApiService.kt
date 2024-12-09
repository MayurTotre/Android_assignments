package com.example.weatherapp.RetrofitResponseCallback

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CityApiService {
    @GET("v1/city")
    fun fetchCityName(
        @Query("name") city: String,
        @Header("X-Api-Key") apiKey: String
    ):Call<CityResponse>
}