package com.example.weatherapp.BroadcastReceiverDemo

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.R

class InternetConnectionMainActivity : AppCompatActivity() {

    private val internetConnectionReceiver = InternetConnectionReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internet_connection_main)
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(internetConnectionReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(internetConnectionReceiver)
    }
}