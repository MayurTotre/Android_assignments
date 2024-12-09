package com.example.weatherapp.RetrofitResponseCallback

import androidx.lifecycle.LiveData
import retrofit2.Callback
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.RetrofitResponseCallback.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class WeatherRepository {
    private val _weatherData= MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

    private val _cityData = MutableLiveData<CityResponse>()
    val cityData: LiveData<CityResponse> = _cityData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val weatherApiService = RetrofitInstance.fetchWeatherData()
    private val cityApiService = RetrofitInstance.fetchCityName()

    fun fetchCityName(city: String){
        val call = cityApiService.fetchCityName(city, "4aFJnZw4xiuEL8e25Qsrew==hUbqFizYLsLj6bqq")
        call.enqueue(object :Callback<CityResponse>{
            override fun onResponse(call: Call<CityResponse>, response: Response<CityResponse>) {
                if (response.isSuccessful){
                    _cityData.postValue(response.body())
                }else{
                    _errorMessage.postValue("Failed to fetch city data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CityResponse>, t: Throwable) {
                _errorMessage.postValue("Failed to fetch city data: ${t.localizedMessage}")
            }
        })
    }

    fun weatherData(location: String, days: Int){
        val call = weatherApiService.fetchWeatherData("55ae7587d2194b30bf364918242111",location, days)
        call.enqueue(object: Callback<WeatherResponse>{
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful){
                    _weatherData.postValue(response.body())
                }else{
                    _errorMessage.postValue("Failed to fetch weather data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                _errorMessage.postValue("Failed to fetch weather data: ${t.localizedMessage}}")
            }

        })
    }
}