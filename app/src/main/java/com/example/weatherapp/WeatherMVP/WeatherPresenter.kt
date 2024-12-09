package com.example.weatherapp.WeatherMVP

import com.example.weatherapp.WeatherMVC.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherPresenter(private val view: WeatherView){
    val weatherApi = RetrofitClient.retrofitInstance.create(WeatherApi::class.java)

    fun fetchWeather(location: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = weatherApi.getWeather(
                    apiKey = "55ae7587d2194b30bf364918242111",
                    location = location,
                    days = 1
                )
                if (response.isSuccessful) {
                    response.body()?.let { weatherData ->
                        CoroutineScope(Dispatchers.Main).launch {
                            view.showWeather(weatherData)
                        }
                    } ?: run {
                        CoroutineScope(Dispatchers.Main).launch {
                            view.showError("No weather data found.")
                        }
                    }
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        view.showError("Error: ${response.code()} - ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                CoroutineScope(Dispatchers.Main).launch {
                    view.showError("Network Error: ${e.localizedMessage}")
                }
            }
        }

    }
}