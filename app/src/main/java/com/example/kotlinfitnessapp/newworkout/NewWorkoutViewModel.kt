package com.example.kotlinfitnessapp.newworkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinfitnessapp.application.CONSTATNTS
import com.example.kotlinfitnessapp.model.Exercise
import com.example.kotlinfitnessapp.model.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewWorkoutViewModel @Inject constructor(private val newWorkoutRepository: NewWorkoutRepository) :ViewModel(){

    private var toastMessage = MutableLiveData<String>()
    private var workoutName = MutableLiveData<String>()
    private var weightValue = MutableLiveData<String>()
    private var setsValue = MutableLiveData<String>()
    private var workoutId:String? = null

    init{
        toastMessage.value="none"
        workoutName.value=""
    }

    fun setWorkoutName(name:String){
        workoutName.value=name
    }
    fun setToastMessage() {
        toastMessage.value="none"
    }

    fun getWorkoutName() = workoutName
    fun getSetsValue() = setsValue
    fun getWeightValue() = weightValue
    fun getToastMessage()= toastMessage

    fun addWorkout(name:String){
        if(name.isEmpty()&&workoutId==null){
            toastMessage.value="Add workout name."
            return
        }
        if(name.length>CONSTATNTS.maxWorkoutNameLength){
            toastMessage.value="Workout name too long."
            return
        }
        if(workoutId==null){
            workoutId = generateRandomString()
            val workout = Workout(workoutId!!,name)
            newWorkoutRepository.addWorkout(workout)
            toastMessage.value="Workout created."
        }
    }

    fun addExercise(exerciseName:String,sets:String,reps:String,rest:String,weight:String){
        if(exerciseName.isEmpty()||reps.isEmpty()||rest.isEmpty()){
            toastMessage.value="Add exercise info."
            return
        }
        if(exerciseName.length>CONSTATNTS.maxExerciseNameLength){
            toastMessage.value="Exercise name too long."
            return
        }
        if(workoutId==null)return
        val exerciseId = generateRandomString()
        val numWeight = Integer.parseInt(weight)
        val numReps = Integer.parseInt(reps)
        val numSets = Integer.parseInt(sets)
        val numRest = Integer.parseInt(rest)
        val exercise = Exercise(exerciseId,workoutId!!,exerciseName,numWeight,numReps,numRest,numSets)
        newWorkoutRepository.addExercise(exercise)
        toastMessage.value="Exercise added."

    }

    fun addValue(currentValue: String,num:Int,choiceValue:String){
        var numCValue = Integer.parseInt(currentValue)
        numCValue+=num
        if(choiceValue=="Sets"&&numCValue!=0){
            setsValue.value=""+numCValue
        }
        if(choiceValue=="Weight"&&numCValue!=0){
            weightValue.value=""+numCValue
        }
    }

    fun setWorkoutId(){
        workoutId="s"
    }

    private fun generateRandomString(len: Int = 30): String{
        val alphanumerics = CharArray(26) { (it + 97).toChar() }.toSet()
            .union(CharArray(9) { (it + 48).toChar() }.toSet())
        return (0 until len).map {
            alphanumerics.toList().random()
        }.joinToString("")
    }

}