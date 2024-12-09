package com.example.weatherapp.WorkmanagerDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainWorkManagerDemoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityWorkManagerDemo : AppCompatActivity() {
    private lateinit var binding: ActivityMainWorkManagerDemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainWorkManagerDemoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val database = ContactDatabase.getDatabase(this)

        val contact = Contact(0,"Mayur", "9898898876")

        lifecycleScope.launch(Dispatchers.IO) {
            database.contactDao().insertContact(contact)
        }

        binding.btnSync.setOnClickListener {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(this).enqueue(workRequest)
        }
    }
}