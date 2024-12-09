package com.example.weatherapp.WeatherMVVMRxJava.View

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.WeatherMVVMRxJava.ApiService
import com.example.weatherapp.WeatherMVVMRxJava.RetrofitClient
import com.example.weatherapp.WeatherMVVMRxJava.ViewModel.WeatherViewModel
import com.example.weatherapp.WeatherMVVMRxJava.ViewModel.WeatherViewModelFactory
import com.example.weatherapp.WeatherMVVMRxJava.WeatherRepository
import com.example.weatherapp.WeatherMVVMRxJava.WeatherResponse

class MainActivityWeatherMVVMRxjava : AppCompatActivity() {

    private lateinit var tvTemperature: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvErrorMessage: TextView

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_weather_mvvmrxjava)

        // Initialize UI components
        tvTemperature = findViewById(R.id.tvTemperature)
        tvLocation = findViewById(R.id.tvLocation)
        tvCondition = findViewById(R.id.tvCondition)
        tvErrorMessage = findViewById(R.id.tvErrorMessage)

        // Initialize ApiService using RetrofitClient
        val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)

        // Initialize WeatherRepository with the ApiService
        weatherRepository = WeatherRepository(apiService)

        // Initialize WeatherViewModel with ViewModelProvider
        weatherViewModel = ViewModelProvider(
            this,
            WeatherViewModelFactory(weatherRepository)
        ).get(WeatherViewModel::class.java)

        // Fetch weather data
        weatherViewModel.fetchWeather("55ae7587d2194b30bf364918242111", "Mumbai", 1, "no", "no")

        // Observe weather data
        weatherViewModel.weatherData.observe(this) { weatherResponse ->
            weatherResponse?.let {
                showWeather(it)
            }
        }

        // Observe error messages
        weatherViewModel.errorMessage.observe(this) { errorMessage ->
            showError(errorMessage)
        }
    }

    private fun showWeather(weather: WeatherResponse) {
        val temperature = weather.current.temp_c
        val locationName = weather.location.name
        val condition = weather.current.condition.text

        tvTemperature.text = "$temperature°C"
        tvLocation.text = locationName
        tvCondition.text = condition
        tvErrorMessage.text = ""
    }

    private fun showError(errorMessage: String) {
        tvErrorMessage.text = errorMessage
        Log.e("Error", errorMessage)
        Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
    }
}
