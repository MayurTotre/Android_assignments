package com.example.weatherapp.WeatherVolley

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley

class VolleyHelper private constructor(context: Context){

    private val requestQueue: RequestQueue = Volley.newRequestQueue(context.applicationContext)
        companion object{
            @Volatile
            private var INSTANCE: VolleyHelper? = null

            fun getInstance(context: Context) =
                INSTANCE ?: synchronized(this){
                    INSTANCE ?: VolleyHelper(context).also { INSTANCE = it }
                }
        }

    fun <T> addToRequestQueue(req: Request<T>){
        requestQueue.add(req)
    }
}