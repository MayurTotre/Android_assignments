package com.example.weatherapp.RetrofitResponseCallback

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)