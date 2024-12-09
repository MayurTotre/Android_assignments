package com.example.weatherapp.WeatherNoArchitecture

data class WeatherRequest(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)