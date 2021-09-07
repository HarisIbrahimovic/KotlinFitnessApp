package com.example.kotlinfitnessapp.newworkout

import com.example.kotlinfitnessapp.daos.ExerciseDao
import com.example.kotlinfitnessapp.daos.WorkoutDao
import com.example.kotlinfitnessapp.model.Exercise
import com.example.kotlinfitnessapp.model.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class RealNewWorkoutRepository @Inject constructor(private val workoutDao: WorkoutDao,private val exerciseDao: ExerciseDao): NewWorkoutRepository {

    override suspend fun addWorkout(workout: Workout) {
            workoutDao.insertWorkout(workout)
            val dbRef = FirebaseDatabase.getInstance().getReference("UsersKotlin")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("Workout")
                .child(workout.id)
            dbRef.setValue(workout)
    }

    override suspend fun addExercise(exercise: Exercise) {
            exerciseDao.addExercise(exercise)
            val dbRef = FirebaseDatabase.getInstance().getReference("UsersKotlin")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("Exercises")
                .child(exercise.id)
            dbRef.setValue(exercise)
    }
}