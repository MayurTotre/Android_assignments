package com.example.weatherapp.RetrofitNestedApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val WEATHER_URL = "http://api.weatherapi.com/"
    private const val CITY_URL = "https://api.api-ninjas.com/"

    fun fetchWeatherData(): WeatherApiService {
        return Retrofit.Builder()
            .baseUrl(WEATHER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    fun fetchCityData(): CityApiService {
        return Retrofit.Builder()
            .baseUrl(CITY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CityApiService::class.java)
    }

}