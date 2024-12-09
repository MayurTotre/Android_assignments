package com.example.weatherapp.WeatherVolley

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.HTTPURL.WeatherViewModelFactory
import com.example.weatherapp.R

class MainActivityWeatherWithVolley : AppCompatActivity() {
    private lateinit var tvTemperature: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvErrorMessage: TextView
    private lateinit var etSearchLocation: EditText
    private lateinit var etLat: EditText
    private lateinit var etLong: EditText
    private lateinit var btnSearch: Button

    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_weather_with_volley)

        etSearchLocation = findViewById(R.id.etSearchLocation)
        etLat = findViewById(R.id.etLat)
        etLong = findViewById(R.id.etLong)
        btnSearch = findViewById(R.id.btnSearch)

        tvTemperature = findViewById(R.id.tvTemperature)
        tvLocation = findViewById(R.id.tvLocation)
        tvCondition = findViewById(R.id.tvCondition)
        tvErrorMessage = findViewById(R.id.tvErrorMessage)


        val repository = WeatherRepository(this)
        val factory = WeatherViewModelFactory(repository)
        val weatherViewModel = ViewModelProvider(this, factory)[WeatherViewModel::class.java]

        btnSearch.setOnClickListener {
            val city = etSearchLocation.text.toString()
            weatherViewModel.fetchCityData(city)

            weatherViewModel.cityData.observe(this) { cityResponse ->
                cityResponse?.let {
                    val cityLat = it[0].get(0).latitude.toString()
                    val cityLong = it[0].get(0).longitude.toString()
                    val city = "$cityLat,$cityLong"

                    etLat.setText(cityLat)
                    etLong.setText(cityLong)

                    val cityLatLong = "${it[0].get(0).name}"
                    weatherViewModel.fetchWeatherData(cityLatLong, 1)
                }
            }

            weatherViewModel.weatherData.observe(this) { weatherResponse ->
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
            weatherViewModel.errorMessage.observe(this) { error ->
                tvErrorMessage.text = tvErrorMessage.text.toString()
                Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
                Log.e("Error", error)
            }
        }
    }
}