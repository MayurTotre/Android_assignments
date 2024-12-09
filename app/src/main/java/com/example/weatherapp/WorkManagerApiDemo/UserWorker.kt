package com.example.weatherapp.WorkManagerApiDemo

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private val database = UserDatabase.getDatabase(applicationContext)

    private var flag = false

    val retrofitInstance = RetrofitClient.insertUser()

    override fun doWork(): Result {
        return if (databaseUpdate()) {
            Log.d("Insert", "${Result.success()}")
            Result.success()
        } else {
            Log.d("Insert", "${Result.failure()}")
            Result.failure()
        }
    }

    fun databaseUpdate(): Boolean {
        runBlocking(Dispatchers.IO) {
            val list = database.userDao().getAllUsers()
            Log.d("Insert", "${list}")
            for (user in list) {
                Log.d("Insert", "${user.first_name}")
                if (!user.flag) {
//                    val userData = User(first_name = user.first_name,
//                        last_name = user.last_name,
//                        email = user.email,
//                        password = user.password,
//                        confirm_password = user.confirm_password,
//                        phone_no = user.phone_no,
//                        gender = user.gender)

                    Log.d("Insert", "${user.first_name}")
                    val userData = retrofitInstance.insertUser(
                        firstName = user.first_name,
                        lastName = user.last_name,
                        email = user.email,
                        password = user.password,
                        confirmPassword = user.confirm_password,
                        phoneNo = user.phone_no.toString(),
                        gender = user.gender
                    )

                    Log.d("Insert", "${userData.isSuccessful}")
                    if(userData.isSuccessful){
                        user.flag = true
                        database.userDao().updateUser(user)
                        flag = true
                    }

                }
            }
        }
        return flag
    }

}