package com.example.weatherapp.RetrofitResponseCallback

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    private val WEATHER_URL = "http://api.weatherapi.com/"
    private val CITY_URL = "https://api.api-ninjas.com/"

    fun fetchWeatherData(): WeatherApiService{
        return Retrofit.Builder()
            .baseUrl(WEATHER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    fun fetchCityName(): CityApiService{
        return Retrofit.Builder()
            .baseUrl(CITY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CityApiService::class.java)
    }


}