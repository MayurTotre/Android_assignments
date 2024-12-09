package com.example.weatherapp.RxJavaNestedCall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.R
import com.example.weatherapp.RxJavaNestedCall.RetrofitClient.fetchWeatherData
import com.example.weatherapp.databinding.ActivityDifferentBinding
import com.example.weatherapp.databinding.ActivityMainBinding

class DIfferentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDifferentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDifferentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cityName = intent.getStringExtra("cityName")?: " "
        binding.etCityName.setText(cityName.toString())

        binding.btnBack.setOnClickListener {
            val cityName = binding.etCityName.text.toString()
            val resultIntent = Intent()
            resultIntent.putExtra("cityName", cityName)
            setResult(RESULT_OK, resultIntent)
            finish()

        }

    }

}