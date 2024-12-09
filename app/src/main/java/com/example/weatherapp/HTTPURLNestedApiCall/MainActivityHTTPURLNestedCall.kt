package com.example.weatherapp.HTTPURLNestedApiCall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R

class MainActivityHTTPURLNestedCall : AppCompatActivity() {
    private lateinit var tvTemperature: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvErrorMessage: TextView
    private lateinit var etSearchLocation: EditText
    private lateinit var etLat: EditText
    private lateinit var etLong: EditText
    private lateinit var btnSearch: Button


    private lateinit var weatheViewModel: WeatherViewModel

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

        val repository = WeatherRepository()
        val factory = WeatherViewModelFactory(repository)
        weatheViewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)



        btnSearch.setOnClickListener {
            weatheViewModel.fetchCityData(etSearchLocation.text.toString())
            Log.d("btn Click", "Button is clicked")
            weatheViewModel.cityData.observe(this) { cityResponse ->
                cityResponse?.let {
                    val cityLat = it[0].latitude.toString()
                    val cityLong = it[0].longitude.toString()
                    val city = "$cityLat,$cityLong"

                    etLat.setText(cityLat)
                    etLong.setText(cityLong)

                    weatheViewModel.fetchWeather(city, 1)

                }
            }

            weatheViewModel.weatherData.observe(this) { weatherResponse ->
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
            weatheViewModel.errorMessage.observe(this) { error ->
                tvErrorMessage.text = tvErrorMessage.text.toString()
                Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
                Log.e("Error", error)
            }
        }


    }
}