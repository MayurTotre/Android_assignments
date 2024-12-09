package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.weatherapp.WeatherMVC.WeatherController
import com.example.weatherapp.WeatherMVC.WeatherRequest
import com.example.weatherapp.WeatherMVC.WeatherView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Console

class MainActivity : AppCompatActivity(), WeatherView {
    private lateinit var weatherController: WeatherController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherController = WeatherController(this)

        CoroutineScope(Dispatchers.Main).launch{
            weatherController.fetchWeather("mumbai")
        }

    }

    override fun displayWeather(weather: WeatherRequest) {
        val temperature = weather.current.temp_c
        val location = weather.location.name
        val condition = weather.current.condition.text

        val tvTemperature: TextView = findViewById(R.id.tvTemperature)
        val tvLocation: TextView = findViewById(R.id.tvLocation)
        val tvCondition: TextView = findViewById(R.id.tvCondition)

        tvTemperature.text = "$temperature°C"
        tvLocation.text = "$location"
        tvCondition.text = "$condition"

    }

    override fun showError(errorMessage: String) {
        val tvErrorMessage: TextView = findViewById(R.id.tvErrorMessage)

        tvErrorMessage.text = errorMessage

        Log.e("Error","Error: ${errorMessage}")
        Toast.makeText(this, "Error: ${errorMessage}", Toast.LENGTH_SHORT).show()
    }
}