package com.example.kotlinfitnessapp.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinfitnessapp.model.Exercise
import com.example.kotlinfitnessapp.model.Workout

class FakeExerciseRepository:ExerciseRepository {

    private val listOfExercise = MutableLiveData<List<Exercise>>()
    private val listOfWorkouts= ArrayList<Workout>()
    private val listOfExerciseA= ArrayList<Exercise>()
    override fun getExerciseList(id: String): LiveData<List<Exercise>> = listOfExercise

    override fun updateExercise(exercise: Exercise) {
        for(i in 0 until listOfExerciseA.size){
            if(exercise.id == listOfExerciseA[i].id){
                listOfExerciseA[i]=exercise
            }
        }
    }

    override fun deleteWorkout(id: String) {
        for(i in 0 until listOfWorkouts.size){
            if(id == listOfWorkouts[i].id){
                listOfWorkouts.removeAt(i)
            }
        }
    }

    override fun deleteExercise(id: String) {
        for(i in 0 until listOfExerciseA.size){
            if(id == listOfExerciseA[i].id){
                listOfExerciseA.removeAt(i)
            }
        }
    }
}