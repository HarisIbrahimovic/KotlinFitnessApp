package com.example.kotlinfitnessapp.menu

import androidx.lifecycle.LiveData
import com.example.kotlinfitnessapp.model.DietDay
import com.example.kotlinfitnessapp.model.User
import com.example.kotlinfitnessapp.model.Workout

interface MenuRepository {
    fun getWorkouts(): LiveData<List<Workout>>
    suspend fun deleteWorkout(id:String)
    fun getCurrentUser():LiveData<User>
    suspend fun addDay(day: DietDay)
    fun getAllDays():LiveData<List<DietDay>>
    suspend fun deleteDay(id: String)
    suspend fun deleteData()

}