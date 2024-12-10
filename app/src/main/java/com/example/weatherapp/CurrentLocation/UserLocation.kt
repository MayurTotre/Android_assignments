package com.example.weatherapp.CurrentLocation

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityUserLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import android.content.pm.PackageManager

class UserLocation : AppCompatActivity() {

    private val LOCATION_PERMISSION_ACCESS_CODE = 100
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: ActivityUserLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        binding.getLocationButton.setOnClickListener { checkLocationPermission() }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is granted, now check if GPS is enabled
            checkGpsStatus()
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

    private fun checkGpsStatus() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder(this)
                .setTitle("GPS Disabled")
                .setMessage("Your GPS is off. Please enable it to get your location.")
                .setPositiveButton("Go to Settings") { _, _ ->
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                    Toast.makeText(this, "Cannot fetch location without GPS.", Toast.LENGTH_SHORT).show()
                }
                .create()
                .show()
        } else {
            // GPS is enabled, proceed to get location
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener(OnCompleteListener { task: Task<Location> ->
            if (task.isSuccessful && task.result != null) {
                val location: Location? = task.result
                val latitude = location?.latitude ?: 0.0
                val longitude = location?.longitude ?: 0.0
                binding.locationText.text = "Latitude: $latitude \nLongitude: $longitude"
            } else {
                Toast.makeText(this, "Failed to get location", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_ACCESS_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, now check GPS status
                checkGpsStatus()
            } else {
                Toast.makeText(this, "Permission denied. Cannot fetch location.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
