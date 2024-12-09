package com.example.weatherapp.HTTPURL

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.weatherapp.R

class MainActivityWeatherHTTPURL : AppCompatActivity() {
    private lateinit var tvTemperature: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvErrorMessage: TextView

    private lateinit var weatheViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_weather_httpurl)

        tvTemperature = findViewById(R.id.tvTemperature)
        tvLocation = findViewById(R.id.tvLocation)
        tvCondition = findViewById(R.id.tvCondition)
        tvErrorMessage = findViewById(R.id.tvErrorMessage)

        val repository = WeatherRepository()
        val factory = WeatherViewModelFactory(repository)
        weatheViewModel = ViewModelProvider(this,factory).get(WeatherViewModel::class.java)

        weatheViewModel.fetchWeather("Mumbai", 1)



        weatheViewModel.weatherData.observe(this){weatherResponse ->
            weatherResponse?.let {
                val temp = it.current.temp_c
                val locationName = it.location
                val conditionText = it.current.condition.text

                Log.d("Weather Response", weatherResponse.toString())
                Log.d("Weather Response Data", temp.toString())
                Log.d("Weather Response Error", locationName.toString())
                Log.d("Weather Response Error", conditionText)


                tvTemperature.text = "$temp°C"
                tvLocation.text = locationName.name.toString()
                tvCondition.text = conditionText
                tvErrorMessage.text = ""
            }
        }

        weatheViewModel.errorMessage.observe(this){error ->
            tvErrorMessage.text = tvErrorMessage.text.toString()
            Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
            Log.e("Error", error)
        }

    }
}