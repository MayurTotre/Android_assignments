package com.example.weatherapp.RetrofitResponseCallback

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class WeatherViewModel(private val repository: WeatherRepository): ViewModel(){
    val weatherData: LiveData<WeatherResponse> = repository.weatherData
    val cityData:LiveData<CityResponse> = repository.cityData
    val errorMessage:LiveData<String> = repository.errorMessage

    fun fetchCityData(city: String){
        return repository.fetchCityName(city)
    }

    fun fetchWeatherData(location: String, days: Int){
        return repository.weatherData(location, days)
    }
}