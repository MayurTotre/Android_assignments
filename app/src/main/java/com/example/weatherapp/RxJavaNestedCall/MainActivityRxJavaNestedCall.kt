package com.example.weatherapp.RxJavaNestedCall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.weatherapp.R

class MainActivityRxJavaNestedCall : AppCompatActivity() {
    private lateinit var tvTemperature: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvErrorMessage: TextView
    private lateinit var etSearchLocation: EditText
    private lateinit var etLat: EditText
    private lateinit var etLong: EditText
    private lateinit var btnSearch: Button
    private lateinit var btnNext: Button
    private lateinit var cityName: String

    private val weatherViewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(WeatherRepository())
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val returnedCityName = data?.getStringExtra("cityName")
            Log.d("ResultLauncher", "Returned City: $returnedCityName")
            if (!returnedCityName.isNullOrEmpty()) {
                Log.d("ResultLauncher", "Returned City: $returnedCityName")
                fetchWeatherData(returnedCityName) // Trigger API refresh
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_rx_java_nested_call)

        // Initialize views
        etSearchLocation = findViewById(R.id.etSearchLocation)
        etLat = findViewById(R.id.etLat)
        etLong = findViewById(R.id.etLong)
        btnSearch = findViewById(R.id.btnSearch)
        btnNext = findViewById(R.id.btnNext)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvLocation = findViewById(R.id.tvLocation)
        tvCondition = findViewById(R.id.tvCondition)
        tvErrorMessage = findViewById(R.id.tvErrorMessage)


        btnSearch.setOnClickListener {
            cityName = etSearchLocation.text.toString().trim()
            if (cityName.isNotEmpty()) {
                fetchWeatherData(cityName) // Fetch weather data for entered city
            }
        }

        btnNext.setOnClickListener {
            Intent(this, DIfferentActivity::class.java).also {
                it.putExtra("cityName", cityName)
                resultLauncher.launch(it) // Launch with result launcher
            }
        }


    }

    private fun fetchWeatherData(cityName: String) {
        weatherViewModel.getCityData(cityName)
        weatherViewModel.cityWeatherData.observe(this, Observer { res ->
            Log.e("PRINT", res.toString())

            val cityResponse = res.cityResponse
            val weatherResponse = res.weatherResponse

            // Update UI with weather data
            etLat.setText(cityResponse[0].latitude.toString())
            etLong.setText(cityResponse[0].longitude.toString())

            tvTemperature.text = "${weatherResponse.current.temp_c}"
            tvLocation.text = weatherResponse.location.name
            tvCondition.text = weatherResponse.current.condition.toString()
        })
    }
}
