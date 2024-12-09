package com.example.weatherapp.RxJavaNestedCall

import io.reactivex.rxjava3.core.Observable


class WeatherRepository{
    private val weatherApiService = RetrofitClient.fetchWeatherData()
    private val cityApiService = RetrofitClient.fetchCityData()


    fun getWeather(lat_long: String, days: Int): Observable<WeatherResponse> {
        return weatherApiService.getWeather(
            apiKey = "55ae7587d2194b30bf364918242111",
            location = lat_long,
            days = days
        )
    }

    fun getCityName(city: String):Observable<CityResponse>{
        return cityApiService.getCityName(
            city = city,
            apiKey = "4aFJnZw4xiuEL8e25Qsrew==hUbqFizYLsLj6bqq"
        )
    }
}