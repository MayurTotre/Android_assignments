package com.example.weatherapp.WeatherMVVMRxJava

import io.reactivex.rxjava3.core.Observable
import com.example.weatherapp.WeatherMVVMRxJava.WeatherResponse
import retrofit2.Response

class WeatherRepository(private val weatherApi: ApiService) {
    fun getWeather(location: String, days: Int): Observable<WeatherResponse> {
        return weatherApi.getWeather(
            apiKey = "55ae7587d2194b30bf364918242111",
            location = location,
            days = days
        )
    }

}