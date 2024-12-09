package com.example.weatherapp.RetrofitNestedApi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WeatherRepository {
    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> get() = _weatherData

    private val _cityData = MutableLiveData<CityResponse>()
    val cityData: LiveData<CityResponse> get() = _cityData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val weatherApiService = RetrofitInstance.fetchWeatherData()
    private val cityApiService = RetrofitInstance.fetchCityData()


    suspend fun fetchCityName(city: String){
        try{
            val response = cityApiService.fetchCityName(city,"4aFJnZw4xiuEL8e25Qsrew==hUbqFizYLsLj6bqq")
            if (response.isSuccessful){
                _cityData.postValue(response.body())
            }else{
                _errorMessage.postValue("Failed to fetch city data: ${response.code()}")
            }
        }catch (e: Exception){
            _errorMessage.postValue("Failed to fetch city data: ${e.localizedMessage}")
        }
    }

    suspend fun fetchWeather(location: String, days: Int){
        try{
            val response = weatherApiService.fetchWeatherData("55ae7587d2194b30bf364918242111", location, days)
            if (response.isSuccessful){
                _weatherData.postValue(response.body())
            }else{
                _errorMessage.postValue("Failed to fetch Weather data: ${response.code()}")
            }
        }catch (e: Exception){
            _errorMessage.postValue("Failed to fetch data: ${e.localizedMessage}")

        }
    }
}