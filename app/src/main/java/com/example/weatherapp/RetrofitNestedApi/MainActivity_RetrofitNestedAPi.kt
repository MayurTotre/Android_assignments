package com.example.weatherapp.RetrofitNestedApi

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.R

class MainActivity_RetrofitNestedAPi : AppCompatActivity() {

    private lateinit var tvTemperature: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvErrorMessage: TextView
    private lateinit var etSearchLocation: EditText
    private lateinit var etLat: EditText
    private lateinit var etLong: EditText
    private lateinit var btnSearch: Button

    private val weatherViewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(WeatherRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_httpurlnested_call)

        etSearchLocation = findViewById(R.id.etSearchLocation)
        etLat = findViewById(R.id.etLat)
        etLong = findViewById(R.id.etLong)
        btnSearch = findViewById(R.id.btnSearch)

        tvTemperature = findViewById(R.id.tvTemperature)
        tvLocation = findViewById(R.id.tvLocation)
        tvCondition = findViewById(R.id.tvCondition)
        tvErrorMessage = findViewById(R.id.tvErrorMessage)

        btnSearch.setOnClickListener {
            val city = etSearchLocation.text.toString()
            weatherViewModel.fetchCityData(city)

            weatherViewModel.cityData.observe(this) { cityResponse ->
                cityResponse?.let {
                    val cityLat = it[0].latitude.toString()
                    val cityLong = it[0].longitude.toString()
                    val cityCoordinates = "$cityLat,$cityLong"

                    etLat.setText(cityLat)
                    etLong.setText(cityLong)

                    weatherViewModel.fetchWeather(cityCoordinates, 1)
                }
            }

            weatherViewModel.weatherData.observe(this) { weatherResponse ->
                weatherResponse?.let {
                    tvTemperature.text = "${it.current.temp_c}°C"
                    tvLocation.text = it.location.name
                    tvCondition.text = it.current.condition.text
                }
            }

            weatherViewModel.errorMessage.observe(this) { error ->
                Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
                tvErrorMessage.text = error
            }
        }
    }
}
