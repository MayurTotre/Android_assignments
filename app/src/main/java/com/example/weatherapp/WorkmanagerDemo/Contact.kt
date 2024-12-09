package com.example.weatherapp.WorkmanagerDemo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ContactDb")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val phoneNo: String,
    var flag: Boolean = false
)
