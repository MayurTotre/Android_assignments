package com.example.weatherapp.HTTPURL

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)