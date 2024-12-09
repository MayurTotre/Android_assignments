package com.example.weatherapp.RxJavaNestedCall

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)