package com.example.kotlinfitnessapp.newworkout

import com.example.kotlinfitnessapp.model.Exercise
import com.example.kotlinfitnessapp.model.Workout

interface NewWorkoutRepository {
    fun addWorkout(workout:Workout)
    fun addExercise(exercise: Exercise)
}