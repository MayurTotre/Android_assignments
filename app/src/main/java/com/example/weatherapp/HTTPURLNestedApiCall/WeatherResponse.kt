package com.example.weatherapp.HTTPURLNestedApiCall

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)