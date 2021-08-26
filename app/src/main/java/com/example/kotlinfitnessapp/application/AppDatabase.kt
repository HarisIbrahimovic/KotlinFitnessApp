package com.example.kotlinfitnessapp.application

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlinfitnessapp.daos.DayDao
import com.example.kotlinfitnessapp.daos.ExerciseDao
import com.example.kotlinfitnessapp.daos.UserDao
import com.example.kotlinfitnessapp.daos.WorkoutDao
import com.example.kotlinfitnessapp.model.DietDay
import com.example.kotlinfitnessapp.model.Exercise
import com.example.kotlinfitnessapp.model.User
import com.example.kotlinfitnessapp.model.Workout

@Database(entities = [Workout::class,Exercise::class, User::class,DietDay::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dayDao() : DayDao
    abstract fun userDao() : UserDao
    abstract fun workoutDao() : WorkoutDao
    abstract fun exerciseDao() : ExerciseDao
}