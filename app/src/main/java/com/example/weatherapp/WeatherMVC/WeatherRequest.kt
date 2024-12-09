package com.example.weatherapp.WeatherMVC

data class WeatherRequest(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)