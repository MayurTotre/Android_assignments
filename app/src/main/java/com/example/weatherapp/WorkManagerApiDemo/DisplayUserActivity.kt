package com.example.weatherapp.WorkManagerApiDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityDisplayUserBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

class DisplayUserActivity : AppCompatActivity(), DisplayUserAdapter.onClickDelete {
    private lateinit var binding: ActivityDisplayUserBinding
    private lateinit var user: List<User>
    private lateinit var database: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDisplayUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


         database = UserDatabase.getDatabase(applicationContext)


        runBlocking(Dispatchers.IO) {
            user = database.userDao().getAllUsers()
        }

        Log.d("Temp Data", user.toString())

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = DisplayUserAdapter(user, this)


        binding.btnSync.setOnClickListener {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = PeriodicWorkRequest.Builder(UserWorker::class.java,15,TimeUnit.MINUTES )
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(this).enqueue(workRequest)
        }
    }

    override fun deleteUser(user: User) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("delete this user")
            .setCancelable(true)
            .setMessage("Confirm Delete?")
            .setPositiveButton("yes") { _, _ ->
                lifecycleScope.launch {
                    database.userDao().deleteUser(user)
                }
            }
            .create()
            .show()
    }
}