package com.example.kotlinfitnessapp.exercises

import androidx.lifecycle.LiveData
import com.example.kotlinfitnessapp.model.Exercise

interface ExerciseRepository {
    fun getExerciseList(id:String): LiveData<List<Exercise>>
    fun updateExercise(exercise: Exercise)
    fun deleteWorkout(id:String)
    fun deleteExercise(id: String)
}