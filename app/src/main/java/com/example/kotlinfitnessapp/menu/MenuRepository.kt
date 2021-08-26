package com.example.kotlinfitnessapp.menu

import androidx.lifecycle.LiveData
import com.example.kotlinfitnessapp.model.DietDay
import com.example.kotlinfitnessapp.model.User
import com.example.kotlinfitnessapp.model.Workout

interface MenuRepository {
    fun getWorkouts(): LiveData<List<Workout>>
    fun deleteWorkout(id:String)
    fun getCurrentUser():LiveData<User>
    fun addDay(day: DietDay)
    fun getAllDays():LiveData<List<DietDay>>
    fun deleteDay(id: String)
    fun deleteData()
}