package com.example.kotlinfitnessapp.exercises

import androidx.lifecycle.LiveData
import com.example.kotlinfitnessapp.model.Exercise

interface ExerciseRepository {
    fun getExerciseList(id:String): LiveData<List<Exercise>>
    suspend fun updateExercise(exercise: Exercise)
    suspend fun deleteWorkout(id:String)
    suspend fun deleteExercise(id: String)
}