package com.example.weatherapp.WeatherNoArchitecture

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MainActivityNoArcitecture : AppCompatActivity() {

    // Declare UI components
    private lateinit var tvTemperature: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvErrorMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_no_arcitecture)

        // Initialize UI components
        tvTemperature = findViewById(R.id.tvTemperature)
        tvLocation = findViewById(R.id.tvLocation)
        tvCondition = findViewById(R.id.tvCondition)
        tvErrorMessage = findViewById(R.id.tvErrorMessage)

        // Fetch weather data
        CoroutineScope(Dispatchers.Main).launch {
            fetchWeather("Kalyan")
        }
    }

    private suspend fun fetchWeather(location: String) {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val weatherApi = retrofit.create(WeatherApi::class.java)

            val response: Response<WeatherRequest> = weatherApi.getWeather(
                apiKey = "55ae7587d2194b30bf364918242111",
                location = location,
                days = 1
            )

            if (response.isSuccessful) {
                response.body()?.let { weatherData ->
                    displayWeather(weatherData)
                } ?: showError("No weather data found.")
            } else {
                showError("Error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            showError("Network Error: ${e.localizedMessage}")
        }
    }

    private fun displayWeather(weather: WeatherRequest) {
        val temperature = weather.current.temp_c
        val locationName = weather.location.name
        val condition = weather.current.condition.text

        tvTemperature.text = "$temperature°C"
        tvLocation.text = locationName
        tvCondition.text = condition
    }

    private fun showError(errorMessage: String) {
        tvErrorMessage.text = errorMessage
        Log.e("Error", errorMessage)
        Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
    }
    interface WeatherApi {
        @GET("forecast.json")
        suspend fun getWeather(
            @Query("key") apiKey: String,
            @Query("q") location: String,
            @Query("days") days: Int,
            @Query("aqi") aqi: String = "no",
            @Query("alerts") alerts: String = "no"
        ): Response<WeatherRequest>
    }

}
