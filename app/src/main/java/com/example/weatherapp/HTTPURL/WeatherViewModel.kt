package com.example.weatherapp.HTTPURL

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository): ViewModel() {

    val weatherData = repository.weatherData
    val errorMessage = repository.errorMessage

    fun fetchWeather(location: String, days: Int){
        Log.d("WeatherViewModel", "fetchWeather called with location: $location, days: $days") // Log input params
        viewModelScope.launch {
             repository.fetchWeather(location, days)
        }
    }
}