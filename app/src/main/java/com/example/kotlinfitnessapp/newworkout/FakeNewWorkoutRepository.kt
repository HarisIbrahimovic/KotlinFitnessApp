package com.example.kotlinfitnessapp.newworkout

import com.example.kotlinfitnessapp.model.Exercise
import com.example.kotlinfitnessapp.model.Workout

class FakeNewWorkoutRepository:NewWorkoutRepository {

    val listOfWorkouts = ArrayList<Workout>()
    val listOfExercises = ArrayList<Exercise>()

    override suspend fun addWorkout(workout: Workout) {
        listOfWorkouts.add(workout)
    }

    override suspend fun addExercise(exercise: Exercise) {
        listOfExercises.add(exercise)
    }
}