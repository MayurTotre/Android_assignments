package com.example.weatherapp.LaunchModes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainLaunchModes2Binding

class MainActivityLaunchModes : AppCompatActivity() {
    private lateinit var binding: ActivityMainLaunchModes2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainLaunchModes2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        StackRegistry.addActivity(javaClass.simpleName)
        binding.currentActivity.text = StackRegistry.displayStack()

        binding.ActivityA.setOnClickListener {
            Intent(this, MainActivity_aa::class.java)
                .also { startActivity(it) }
        }

        binding.ActivityB.setOnClickListener {
            Intent(this, MainActivity_bb::class.java)
                .also { startActivity(it) }
        }

        binding.ActivityC.setOnClickListener {
            Intent(this, MainActivity_cc::class.java)
                .also { startActivity(it) }
        }

        binding.ActivityD.setOnClickListener {
            Intent(this, MainActivity_dd::class.java)
                .also { startActivity(it) }
        }


    }
}