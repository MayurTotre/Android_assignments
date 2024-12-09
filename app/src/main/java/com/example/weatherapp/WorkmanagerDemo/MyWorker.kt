package com.example.weatherapp.WorkmanagerDemo

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyWorker(context: Context, parameters: WorkerParameters): Worker(context, parameters) {
    private val database = ContactDatabase.getDatabase(applicationContext)

    private var flag = false

    override fun doWork(): Result {
        return if (databaseUpdate()){
            Result.success()
        }else{
            Result.failure()
        }
    }

    fun databaseUpdate(): Boolean{
        GlobalScope.launch {
            val list = database.contactDao().getAllContacts()
            try {

                for (contact in list){
                    if(!contact.flag){
                        Log.d("MyContactWorker", "${contact.name} ${contact.phoneNo}")
                        contact.flag = true
                        flag = true
                        database.contactDao().updateContact(contact)
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
                flag = false
            }
        }
        return flag
    }

}