package com.example.weatherapp.HTTPURL

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object NetworkHelper {

    

    suspend fun fetchData(location:String, days: Int): String{
        return withContext(Dispatchers.IO){

            var response = ""

            val apiKey = "55ae7587d2194b30bf364918242111"
            val apiUrl = "https://api.weatherapi.com/v1/forecast.json?key=$apiKey&q=$location&days=$days&aqi=no&alerts=no"

            val url = URL(apiUrl)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 15000
            connection.doInput = true

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            response = reader.readText()

            Log.d("Weather Response", response.toString())
            reader.close()

            response
        }

    }
}