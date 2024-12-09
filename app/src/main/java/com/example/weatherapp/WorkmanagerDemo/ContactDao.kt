package com.example.weatherapp.WorkmanagerDemo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface ContactDao {
    @Insert
    abstract fun insertContact(contact: Contact)

    @Query("SELECT * FROM CONTACTDB")
    abstract fun getAllContacts(): List<Contact>

    @Update
    abstract fun updateContact(contact: Contact)
}