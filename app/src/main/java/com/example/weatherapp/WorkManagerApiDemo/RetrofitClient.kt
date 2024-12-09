package com.example.weatherapp.WorkManagerApiDemo

import com.example.weatherapp.RetrofitNestedApi.RetrofitInstance
import com.example.weatherapp.RetrofitNestedApi.WeatherApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val BASE_URL = "http://staging.php-dev.in:8844/trainingapp/api/"

        fun insertUser(): APIServices {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIServices::class.java)
        }
}