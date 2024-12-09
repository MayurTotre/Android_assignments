package com.example.weatherapp.WithoutAsync

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)