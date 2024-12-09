package com.example.weatherapp.WorkManagerApiDemo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val first_name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val confirm_password: String,
    val gender: String,
    val phone_no: Long,
    var flag: Boolean = false
)
