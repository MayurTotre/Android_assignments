package com.example.weatherapp.WeatherMVVMRxJava.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.WeatherMVVMRxJava.WeatherRepository

class WeatherViewModelFactory(
    private val weatherRepository:WeatherRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(WeatherViewModel::class.java)){
            WeatherViewModel(weatherRepository) as T
        }else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}