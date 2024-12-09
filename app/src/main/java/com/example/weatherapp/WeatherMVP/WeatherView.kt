package com.example.weatherapp.WeatherMVP

import com.example.weatherapp.WeatherMVC.WeatherRequest

interface WeatherView {
    fun showWeather(weather: WeatherRequest)
    fun showError(errorMessage: String)
}