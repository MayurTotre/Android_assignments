package com.example.weatherapp.RxJavaNestedCall

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("city")
    fun getCityName(
        @Query("name") city: String,
        @Header("X-Api-Key") apiKey: String
    ): Observable<CityResponse>


    @GET("forecast.json")
    fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int
    ): Observable<WeatherResponse>
}