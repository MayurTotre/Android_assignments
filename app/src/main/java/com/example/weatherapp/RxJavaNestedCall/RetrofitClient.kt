package com.example.weatherapp.RxJavaNestedCall

import com.example.weatherapp.RxJavaNestedCall.ApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {
    private val WEATHER_URL = "https://api.weatherapi.com/v1/"
    private val CITY_URL = "https://api.api-ninjas.com/v1/"

    fun fetchWeatherData(): ApiService{
        return Retrofit.Builder()
            .baseUrl(WEATHER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    fun fetchCityData():ApiService{
        return Retrofit.Builder()
            .baseUrl(CITY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}