package com.example.weatherapp.WeatherVolley

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.WeatherVolley.WeatherRepository
import com.example.weatherapp.WeatherVolley.WeatherViewModel

class WeatherViewModelFactory(private val repository: WeatherRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)){
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class!")
    }
}