package com.example.kotlinfitnessapp.newworkout

import com.example.kotlinfitnessapp.model.Exercise
import com.example.kotlinfitnessapp.model.Workout

interface NewWorkoutRepository {
    suspend fun addWorkout(workout:Workout)
    suspend fun addExercise(exercise: Exercise)
}