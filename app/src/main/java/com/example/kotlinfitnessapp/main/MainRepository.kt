package com.example.kotlinfitnessapp.main

import androidx.lifecycle.LiveData
import com.example.kotlinfitnessapp.model.User

interface MainRepository {
    fun loginUser(email:String,password:String)
    fun createUser(email: String,password: String,userName:String,weight:Int,height:Int)
    fun getUser(): LiveData<Boolean>
    fun addToList(user: User)
    fun getExercises()
    fun getWorkouts()
    fun getDays()
}