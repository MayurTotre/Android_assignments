package com.example.weatherapp.WeatherMVVMRxJava

data class WeatherRequest(
    val key: String,
    val location: String,
    val days: Int,
    val aqi: String,
    val alerts: String
)