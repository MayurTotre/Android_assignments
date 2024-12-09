package com.example.weatherapp.WeatherMVP

data class WeatherRequest(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)