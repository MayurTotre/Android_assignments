package com.example.weatherapp.CurrentLocation2

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.weatherapp.databinding.ActivityUserLocationBinding

class UserLocation : AppCompatActivity() {

    private val LOCATION_PERMISSION_ACCESS_CODE = 100
    private lateinit var locationManager: LocationManager
    private lateinit var binding: ActivityUserLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the ViewBinding
        binding = ActivityUserLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Set up the button click listener
        binding.getLocationButton.setOnClickListener { checkLocationPermission() }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            checkNetworkStatus()
        } else {
            // Request permission if not granted
            AlertDialog.Builder(this)
                .setTitle("Location Permission")
                .setMessage("This app needs location permission!")
                .setPositiveButton("OK") { _, _ -> requestLocationPermission() }
                .create()
                .show()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_ACCESS_CODE
        )
    }

    private fun checkNetworkStatus() {
        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            AlertDialog.Builder(this)
                .setTitle("Network Disabled")
                .setMessage("Your network provider is off. Please enable it to get your location.")
                .setPositiveButton("Go to Settings") { _, _ ->
                    val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(intent)
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                    Toast.makeText(
                        this,
                        "Cannot fetch location without Network.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .create()
                .show()
        } else {
            // Network provider is enabled, get location using LocationManager
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        val criteria = Criteria()
        val provider = locationManager.getBestProvider(criteria, true)

        if (provider != null) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            locationManager.requestLocationUpdates(
                provider,
                1000L,
                1f,
                object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        binding.locationText.text = "Latitude: $latitude \nLongitude: $longitude"
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

                    override fun onProviderEnabled(provider: String) {}

                    override fun onProviderDisabled(provider: String) {}
                })
        } else {
            Toast.makeText(this, "No location provider available", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_ACCESS_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkNetworkStatus()
            } else {
                Toast.makeText(this, "Permission denied. Cannot fetch location.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
