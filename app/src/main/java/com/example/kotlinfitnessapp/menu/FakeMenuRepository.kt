package com.example.kotlinfitnessapp.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinfitnessapp.model.DietDay
import com.example.kotlinfitnessapp.model.Exercise
import com.example.kotlinfitnessapp.model.User
import com.example.kotlinfitnessapp.model.Workout
import java.util.ArrayList

class FakeMenuRepository : MenuRepository {

    val listOfWorkouts = ArrayList<Workout>()
    private val listOfDaysA = ArrayList<DietDay>()
    private val listOfExerciseA = ArrayList<Exercise>()
    private val userValue = User("",0,0,"","","")
    private val listOfDays = MutableLiveData<List<DietDay>>()
    private val listOfExercise = MutableLiveData<List<Exercise>>()
    private val mList = MutableLiveData<List<Workout>>()
    private val cUser = MutableLiveData<User>()

    override fun getWorkouts(): LiveData<List<Workout>> = mList

    override fun deleteWorkout(id: String) {
        var num =-1
        for(i in 0 until listOfWorkouts.size){
            if(id == listOfWorkouts[i].id) {
                num=i
                break
            }
        }
        if(num!=-1) listOfWorkouts.removeAt(num)
    }

    override fun getCurrentUser():LiveData<User> =cUser

    override fun addDay(day: DietDay) {
        listOfDaysA.add(day)
        listOfDays.value=listOfDaysA
    }

    override fun getAllDays(): LiveData<List<DietDay>> = listOfDays

    override fun deleteDay(id: String) {
        for(i in 0 until listOfDaysA.size){
            if(id == listOfDaysA[i].id) {
                listOfDaysA.removeAt(i)
                break
            }
        }
        listOfDays.value=listOfDaysA
    }

    override fun deleteData() {

        val listOfWorkouts = ArrayList<Workout>()
        val listOfDaysA = ArrayList<DietDay>()
        val listOfExerciseA = ArrayList<Exercise>()
        val userValue = User("",0,0,"","","")
        //setValuesToEmptyArraylists
        listOfDays.value = listOfDaysA
        mList.value = listOfWorkouts
        cUser.value = userValue
        listOfExercise.value = listOfExerciseA
    }
}