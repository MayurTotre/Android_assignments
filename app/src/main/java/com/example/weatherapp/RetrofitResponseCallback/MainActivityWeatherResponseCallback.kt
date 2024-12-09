package com.example.weatherapp.RetrofitResponseCallback

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.weatherapp.R
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.location.LocationServices



class MainActivityWeatherResponseCallback : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val REQUEST_CODE_LOCATION_PERMISSION = 1

    private lateinit var tvTemperature: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvErrorMessage: TextView
    private lateinit var etSearchLocation: EditText
    private lateinit var etLat: EditText
    private lateinit var etLong: EditText
    private lateinit var btnSearch: Button
  private lateinit var tvCityName: TextView

    private val weatherViewModel:  WeatherViewModel by viewModels{
        WeatherViewModelFactory(WeatherRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_weather_response_callback)

        etSearchLocation = findViewById(R.id.etSearchLocation)
        etLat = findViewById(R.id.etLat)
        etLong = findViewById(R.id.etLong)
        btnSearch = findViewById(R.id.btnSearch)

        tvTemperature = findViewById(R.id.tvTemperature)
        tvLocation = findViewById(R.id.tvLocation)
        tvCondition = findViewById(R.id.tvCondition)
        tvErrorMessage = findViewById(R.id.tvErrorMessage)
        tvCityName = findViewById<TextView>(R.id.tvLocation)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkLocationPermission()

        btnSearch.setOnClickListener {
                val city = etSearchLocation.text.trim().toString()
                weatherViewModel.fetchCityData(city)

            weatherViewModel.cityData.observe(this){response ->
                response?.let {
                    val cityLat = it[0].latitude.toString()
                    val cityLong = it[0].longitude.toString()
                    val cityLatLong = "$cityLat,$cityLong"

                    etLat.setText(cityLat)
                    etLong.setText(cityLong)

                    weatherViewModel.fetchWeatherData(cityLatLong,1)
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
        tvLocation.setOnClickListener {
            val city = tvCityName.text.toString()
            searchLocation(city)
        }

    }
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION_PERMISSION
            )
        }
    }

    private fun getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener(this, OnSuccessListener { location ->
                if (location != null) {
                    // Use location data (latitude and longitude)
                    Log.d("Location", "Latitude: ${location.latitude}, Longitude: ${location.longitude}")
                    Toast.makeText(this, "Location: ${location.latitude}, ${location.longitude}", Toast.LENGTH_SHORT).show()
                } else {
                    // Location is null, maybe GPS is off or unavailable
                    Log.d("Location", "No location available.")
                    Toast.makeText(this, "No location available", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun searchLocation(cityName: String){

//        val searchUrl = "https://www.google.com/search?q=$cityName"

                val searchUrl = "https://www.google.com/maps/place/$cityName"

        val webView = findViewById<WebView>(R.id.SearchWebView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl(searchUrl)
    }


}