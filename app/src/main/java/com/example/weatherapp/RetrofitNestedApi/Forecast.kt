package com.example.weatherapp.RetrofitNestedApi

data class Forecast(
    val forecastday: List<Forecastday>
)