package com.example.weatherapp.WeatherVolley

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.WeatherVolley.CityResponse
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository): ViewModel() {

    val cityData = repository.cityData
    val weatherData = repository.weatherData
    val errorMessage = repository.errorMessage

    fun fetchCityData(city: String){
        viewModelScope.launch {
            repository.fetchCityName(city)
        }
    }

    fun fetchWeatherData(location: String, days: Int){
        viewModelScope.launch {
            repository.fetchWeather(location, days)
        }
    }
}