package com.example.weatherapp.WeatherVolley

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.JsonObject
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnSuccess

object NetworkHelper {

    fun fetchCityName(context: Context, city: String, onSuccess: (String) -> Unit, onError: (String) -> Unit){
        val apiUrl = "https://api.api-ninjas.com/v1/city?name=$city"
        val apiKey = "4aFJnZw4xiuEL8e25Qsrew==hUbqFizYLsLj6bqq"

        val request = object : JsonObjectRequest(
            Request.Method.GET, apiUrl, null,
            { response ->
                Log.d("City Response", response.toString())
                onSuccess(response.toString())
            },
            {error ->
                Log.d("City Response", error.toString())
                onError(error.toString())
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf("X-Api-Key" to apiKey)
            }
        }
            VolleyHelper.getInstance(context).addToRequestQueue(request)
    }

    fun fetchWeatherData(context: Context, location: String, days: Int, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        val apiKey = "55ae7587d2194b30bf364918242111"
        val apiUrl = "https://api.weatherapi.com/v1/forecast.json?key=$apiKey&q=$location&days=$days&aqi=no&alerts=no"

        val request = JsonObjectRequest(
            Request.Method.GET, apiUrl, null,
            { response ->
                Log.d("Weather Response", response.toString())
                onSuccess(response.toString())
            },
            {error ->
                Log.e("Weather Error", error.toString())
                onError(error.message ?: "An error occurred")
            }
        )
        VolleyHelper.getInstance(context).addToRequestQueue(request)

    }
}