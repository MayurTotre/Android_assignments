package com.example.weatherapp.RetrofitResponseCallback

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface WeatherApiService {
    @GET("v1/forecast.json")
    fun fetchWeatherData(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") day: Int,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ):Call<WeatherResponse>
}