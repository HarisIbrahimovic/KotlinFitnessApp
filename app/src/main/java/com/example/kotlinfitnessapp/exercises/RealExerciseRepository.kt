package com.example.kotlinfitnessapp.exercises

import androidx.lifecycle.LiveData
import com.example.kotlinfitnessapp.daos.ExerciseDao
import com.example.kotlinfitnessapp.daos.WorkoutDao
import com.example.kotlinfitnessapp.model.Exercise
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class RealExerciseRepository@Inject constructor(val workoutDao: WorkoutDao,val exerciseDao: ExerciseDao):ExerciseRepository {

    private lateinit var exerciseList:LiveData<List<Exercise>>

    override fun getExerciseList(id: String): LiveData<List<Exercise>> {
        exerciseList=exerciseDao.getExerciseList(id)
        return exerciseList
    }

    override fun updateExercise(exercise: Exercise) {
        GlobalScope.launch {
            exerciseDao.addExercise(exercise)
        }
        val dbRef = FirebaseDatabase.getInstance().getReference("UsersKotlin").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Exercises")
        dbRef.child(exercise.id).setValue(exercise)
    }

    override fun deleteWorkout(id: String) {
        GlobalScope.launch {
            workoutDao.deleteWorkout(id)
        }
        val dbRef = FirebaseDatabase.getInstance().getReference("UsersKotlin").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Workouts").child(id)
        dbRef.removeValue()
    }

    override fun deleteExercise(id: String) {
        GlobalScope.launch {
            exerciseDao.deleteExs(id)
        }
    }

}