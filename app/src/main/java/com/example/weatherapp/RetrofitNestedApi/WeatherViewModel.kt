package com.example.weatherapp.RetrofitNestedApi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    val weatherData: LiveData<WeatherResponse> = repository.weatherData
    val cityData: LiveData<CityResponse> = repository.cityData
    val errorMessage: LiveData<String> = repository.errorMessage

    fun fetchCityData(city: String) {
        viewModelScope.launch {
            repository.fetchCityName(city)
        }
    }

    fun fetchWeather(location: String, days: Int) {
        viewModelScope.launch {
            repository.fetchWeather(location, days)
        }
    }
}
