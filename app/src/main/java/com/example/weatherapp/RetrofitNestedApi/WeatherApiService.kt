package com.example.weatherapp.RetrofitNestedApi

import com.example.weatherapp.WeatherMVVMRxJava.Day
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherApiService {

    @GET("v1/forecast.json")
    suspend fun fetchWeatherData(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") day: Int,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ):Response<WeatherResponse>
}