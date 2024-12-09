package com.example.weatherapp.LaunchModes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainCcBinding

class MainActivity_cc : AppCompatActivity() {
    private lateinit var binding: ActivityMainCcBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainCcBinding.inflate(layoutInflater)
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