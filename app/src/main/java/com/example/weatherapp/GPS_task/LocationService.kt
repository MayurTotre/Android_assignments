package com.example.weatherapp.GPS_task

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weatherapp.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task

class LocationService : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var latitudeTextView: TextView
    private lateinit var longitudeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_service)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        latitudeTextView = findViewById(R.id.latitudeTextView)
        longitudeTextView = findViewById(R.id.longitudeTextView)

        checkPermissionsAndAccuracy()
    }

    private fun checkPermissionsAndAccuracy() {
        val locationPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
                val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

                if (fineLocationGranted || coarseLocationGranted) {
                    checkGPSAndAccuracy()
                } else {
                    Toast.makeText(this, "Location permissions are required", Toast.LENGTH_SHORT).show()
                }
            }

        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun checkGPSAndAccuracy() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            checkHighAccuracyMode()
        } else {
            showGPSDialog()
        }
    }

    private fun showGPSDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("GPS is required for this feature. Please turn it on.")
            .setCancelable(false)
            .setPositiveButton("Turn On") { dialog, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(this, "GPS is required for location services.", Toast.LENGTH_SHORT).show()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun checkHighAccuracyMode() {
        val settingsClient: SettingsClient = LocationServices.getSettingsClient(this)
        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(
                com.google.android.gms.location.LocationRequest.create().apply {
                    priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
                }
            ).build()

        val task: Task<LocationSettingsResponse> = settingsClient.checkLocationSettings(locationSettingsRequest)

        task.addOnSuccessListener {
            // High accuracy mode is enabled
            getCurrentLocation()
        }

        task.addOnFailureListener {
            // High accuracy mode is not enabled, show a dialog to prompt the user
            showHighAccuracyDialog()
        }
    }

    private fun showHighAccuracyDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("High Accuracy mode is required for this feature. Please enable it.")
            .setCancelable(false)
            .setPositiveButton("Enable High Accuracy") { dialog, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(this, "High Accuracy mode is required for accurate location.", Toast.LENGTH_SHORT).show()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Location permissions are missing.", Toast.LENGTH_SHORT).show()
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                latitudeTextView.text = "Latitude: $latitude"
                longitudeTextView.text = "Longitude: $longitude"
            } else {
                Toast.makeText(this, "Failed to fetch location. Try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
