package com.example.weatherapp.WeatherVolley

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)