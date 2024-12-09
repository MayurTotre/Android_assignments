package com.example.weatherapp.WithoutAsync

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.weatherapp.R

class MainActivityWithoutAsync : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var tvTemperature: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvLocation: TextView
    private lateinit var etCity: EditText
    private lateinit var btnFetch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_without_async)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvCondition = findViewById(R.id.tvCondition)
        tvLocation = findViewById(R.id.tvLocation)
        etCity = findViewById(R.id.etCity)
        btnFetch = findViewById(R.id.btnFetch)

        // Initialize Retrofit
        apiService = ApiClient.retrofit.create(ApiService::class.java)

        btnFetch.setOnClickListener {
            val city = etCity.text.toString().trim()
            fetchWeatherSync(city)
        }
    }

    private fun fetchWeatherSync(city: String) {
        try {
            Thread.sleep(10000) // 10-second delay to force ANR

            // Synchronous call
            val response = apiService.getWeatherSync(
                apiKey = "55ae7587d2194b30bf364918242111", // Your API Key
                city = city,
                days = 1,
                aqi = "no",
                alerts = "no"
            ).execute()

            if (response.isSuccessful) {
                val weather = response.body()
                weather?.let {
                    tvTemperature.text = "Temperature: ${it.current.temp_c}°C"
                    tvCondition.text = "Condition: ${it.current.condition.text}"
                    tvLocation.text = "Location: ${it.location.name}, ${it.location.country}"
                }
            } else {
                tvTemperature.text = "Error: ${response.errorBody()?.string()}"
            }
        } catch (e: Exception) {
            tvTemperature.text = "Exception: ${e.message}"
        }
    }
}