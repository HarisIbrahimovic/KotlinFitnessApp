package com.example.kotlinfitnessapp.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinfitnessapp.model.Exercise
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel@Inject constructor(private val exerciseRepository: ExerciseRepository): ViewModel() {

    private lateinit var exerciseList:LiveData<List<Exercise>>
    private val toastMessage = MutableLiveData<String>()
    fun setExerciseList(id:String){
        exerciseList= exerciseRepository.getExerciseList(id)
    }


    fun getExerciseList() = exerciseList
    fun getToastMessage() = toastMessage

    fun updateExercise(exerciseId:String, workoutId: String, exerciseName: String, sets: String, weight: String, reps: String, rest: String) {
        if(workoutId.isEmpty()||exerciseName.isEmpty()||reps.isEmpty()||rest.isEmpty()){
            toastMessage.value="Fill in the fields."
            return
        }
        val numSets = Integer.parseInt(sets)
        val numWeight = Integer.parseInt(weight)
        val numReps = Integer.parseInt(reps)
        val numRest = Integer.parseInt(rest)
        val exercise:Exercise = if(exerciseId.isEmpty()) Exercise(generateRandomString(),workoutId,exerciseName,numWeight,numReps,numRest,numSets)
        else Exercise(exerciseId,workoutId,exerciseName,numWeight,numReps,numRest,numSets)
        exerciseRepository.updateExercise(exercise)
        toastMessage.value="Done"
    }



    private fun generateRandomString(len: Int = 30): String{
        val alphanumerics = CharArray(26) { (it + 97).toChar() }.toSet()
            .union(CharArray(9) { (it + 48).toChar() }.toSet())
        return (0 until len).map {
            alphanumerics.toList().random()
        }.joinToString("")
    }

    fun deleteWorkout(workoutId: String) {
        exerciseRepository.deleteWorkout(workoutId)
    }

    fun setToastMessage(s: String) {
        toastMessage.value=s
    }

    fun deleteExercise(id: String) {
        exerciseRepository.deleteExercise(id)
    }


}