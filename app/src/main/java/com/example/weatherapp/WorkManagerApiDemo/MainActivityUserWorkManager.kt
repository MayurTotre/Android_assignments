package com.example.weatherapp.WorkManagerApiDemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatherapp.R
import com.example.weatherapp.WorkManagerApiDemo.UserWorker
import com.example.weatherapp.databinding.ActivityMainUserWorkManagerBinding
import com.example.weatherapp.databinding.ActivityMainWorkManagerDemoBinding
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityUserWorkManager : AppCompatActivity() {
    private lateinit var binding: ActivityMainUserWorkManagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainUserWorkManagerBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val database = UserDatabase.getDatabase(applicationContext)

        binding.btnSave.setOnClickListener {
            val firstName = binding.etfirstName.text.toString()
            val lastName = binding.etlastName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etpassword.text.toString()
            val confirmPassword = binding.etConfirmPass.text.toString()
            val gender = binding.etGender.text.toString()
            val phoneNo = binding.etphoneNo.text.toString().toLong()

            val user = User(
                first_name = firstName,
                last_name = lastName,
                email = email,
                password = password,
                confirm_password = confirmPassword,
                phone_no = phoneNo,
                gender = gender)

            lifecycleScope.launch(Dispatchers.IO){
             database.userDao().insertUser(user)
            }

            binding.etGender.text.clear()
            binding.etEmail.text.clear()
            binding.etpassword.text.clear()
            binding.etfirstName.text.clear()
            binding.etlastName.text.clear()
            binding.etConfirmPass.text.clear()
            binding.etphoneNo.text.clear()

        }
        binding.btnSync.setOnClickListener {
            Intent(this,DisplayUserActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}