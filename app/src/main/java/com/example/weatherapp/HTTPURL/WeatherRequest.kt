package com.example.weatherapp.HTTPURL

data class WeatherRequest(
        val key: String,
        val location: String,
        val days: Int,
        val aqi: String,
        val alerts: String
    )
