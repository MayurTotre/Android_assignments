package com.example.weatherapp.WeatherVolley

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class WeatherRepository(private val context: Context) {
    private val gson = Gson()

    private val _cityData = MutableLiveData<List<CityResponse>>()
    val cityData: LiveData<List<CityResponse>> get() = _cityData

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> get() = _weatherData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchCityName(city: String) {
        val url = "https://api.api-ninjas.com/v1/city?name=$city"
        val requestQueue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                try {
                    // Use Gson to parse the JSON array into a list of CityResponse objects
                    val cityListType = object : TypeToken<List<CityResponse>>() {}.type
                    val cityList: List<CityResponse> = gson.fromJson(response, cityListType)
                    _cityData.postValue(cityList)
                } catch (e: Exception) {
                    e.printStackTrace()
                    _errorMessage.postValue("Parsing error: ${e.localizedMessage}")
                }
            },
            { error ->
                _errorMessage.postValue("Network error: ${error.localizedMessage}")
            }
        )

        requestQueue.add(stringRequest)
    }

    fun fetchWeather(location: String, days: Int) {
        val url = "https://api.weatherapi.com/v1/forecast.json?key=YOUR_API_KEY&q=$location&days=$days"
        val requestQueue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                try {
                    // Use Gson to parse the JSON object into a WeatherResponse object
                    val weatherResponse: WeatherResponse = gson.fromJson(response, WeatherResponse::class.java)
                    _weatherData.postValue(weatherResponse)
                } catch (e: Exception) {
                    e.printStackTrace()
                    _errorMessage.postValue("Parsing error: ${e.localizedMessage}")
                }
            },
            { error ->
                _errorMessage.postValue("Network error: ${error.localizedMessage}")
            }
        )

        requestQueue.add(stringRequest)
    }
}