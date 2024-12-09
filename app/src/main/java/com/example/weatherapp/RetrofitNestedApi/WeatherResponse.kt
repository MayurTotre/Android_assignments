package com.example.weatherapp.RetrofitNestedApi

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)