package com.example.weatherapp.ServicesDemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityServicesMainActvityBinding

class ServicesMainActvity : AppCompatActivity() {
    private lateinit var binding: ActivityServicesMainActvityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicesMainActvityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartService.setOnClickListener {
            val serviceIntent = Intent(this, MusicService::class.java)
            startService(serviceIntent)
        }

        binding.btnStopService.setOnClickListener {
            val serviceIntent = Intent(this, MusicService::class.java)
            stopService(serviceIntent)

        }
    }
}