package com.example.weatherapp.WeatherMVP

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.weatherapp.R

class MainActivityMVP : AppCompatActivity(), WeatherView {
    private lateinit var tvTemperature: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvErrorMessage: TextView

    private lateinit var weatherPresenter: WeatherPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_mvp)

        tvTemperature = findViewById(R.id.tvTemperature)
        tvLocation = findViewById(R.id.tvLocation)
        tvCondition = findViewById(R.id.tvCondition)
        tvErrorMessage = findViewById(R.id.tvErrorMessage)

        weatherPresenter = WeatherPresenter(this)

        weatherPresenter.fetchWeather("kalyan")


    }


    override fun showWeather(weather: com.example.weatherapp.WeatherMVC.WeatherRequest) {
        val temperature = weather.current.temp_c
        val locationName = weather.location.name
        val condition = weather.current.condition.text

        tvTemperature.text = "$temperature°C"
        tvLocation.text = locationName
        tvCondition.text = condition    }

    override fun showError(errorMessage: String) {
        tvErrorMessage.text = errorMessage
        Log.e("Error", errorMessage)
        Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
    }
}