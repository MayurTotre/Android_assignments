package com.example.weatherapp.LaunchModes

object StackRegistry {

    val activityStack = mutableListOf<String>()

    fun addActivity(activityName: String){
        activityStack.add(activityName)
    }

    fun removeActivity(activityName: String){
        activityStack.remove(activityName)
    }

    fun displayStack(): String{
        return activityStack.joinToString ( " -> " )
    }
}