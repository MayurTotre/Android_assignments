package com.example.weatherapp.HTTPURLNestedApiCall

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherRepository {
    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> get() = _weatherData

    private val _cityData = MutableLiveData<CityResponse>()
    val cityData: LiveData<CityResponse> get() = _cityData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    suspend fun fetchCityName(city: String){
        try{
            val response = NetworkHelper.fetchCityName(city)
            val gson = Gson()
            val cityResponse = gson.fromJson(response, CityResponse::class.java)
            _cityData.postValue(cityResponse)
        }catch (e: Exception){
            Log.d("City Response Message","Exception")
            _errorMessage.postValue(e.localizedMessage ?: "An error occurred")

        }
    }
    suspend fun fetchWeather(location: String, days: Int){

        try {
                val response = NetworkHelper.fetchData(location, days)
                val gson = Gson()
                val weatherResponse=  gson.fromJson(response,WeatherResponse::class.java)
                _weatherData.postValue(weatherResponse)
//            }else{
//                Log.d("Weather Response msg", connection.responseMessage.toString())
//                _errorMessage.postValue("Error: ${connection.responseMessage}")
//
//            }
//            connection.disconnect()
        }catch (e:Exception){
            Log.d("Weather Response msg","Exception")
            _errorMessage.postValue(e.localizedMessage ?: "An error occurred")
        }
    }

}