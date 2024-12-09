package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TempActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)

        val button = findViewById<Button>(R.id.btnCrash)

        button.setOnClickListener {
            throw RuntimeException("Test Exception!")
        }
    }
}