package com.example.weatherapp.WeatherMVVMRxJava

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)