package com.example.weatherapp.RxJavaNestedCall

data class CityWeatherResponse(
    val cityResponse: CityResponse,
    val weatherResponse: WeatherResponse
)
