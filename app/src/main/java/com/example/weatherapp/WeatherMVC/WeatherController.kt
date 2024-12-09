package com.example.weatherapp.WeatherMVC

import com.example.weatherapp.MainActivity
import retrofit2.Retrofit

class WeatherController(private val view: WeatherView) {
    private val weatherApi = RetrofitClient.retrofitInstance.create(WeatherApi::class.java)

    suspend fun fetchWeather(location: String){
        try {
            val response = weatherApi.getWeather(
                apiKey = "55ae7587d2194b30bf364918242111",
                location = location,
                days = 1
            )
            if (response.isSuccessful){
                response.body()?.let { weatherData ->
                    view.displayWeather(weatherData)
                }
            }else{
                view.showError("Error: ${response.code()} - ${response.message()}")
            }
        }catch (e: Exception){
            view.showError("Network Error: ${e.localizedMessage}")
        }
    }
}

interface WeatherView{
    fun displayWeather(weather: WeatherRequest)
    fun showError(errorMessage: String)
}