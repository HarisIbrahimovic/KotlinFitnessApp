package com.example.kotlinfitnessapp.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinfitnessapp.daos.DayDao
import com.example.kotlinfitnessapp.daos.ExerciseDao
import com.example.kotlinfitnessapp.daos.UserDao
import com.example.kotlinfitnessapp.daos.WorkoutDao
import com.example.kotlinfitnessapp.model.DietDay
import com.example.kotlinfitnessapp.model.User
import com.example.kotlinfitnessapp.model.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class RealMenuRepository @Inject constructor(private val workoutDao: WorkoutDao,private val userDao: UserDao,private val dayDao: DayDao,private val exerciseDao: ExerciseDao) : MenuRepository {

    private lateinit var listOfWorkouts : LiveData<List<Workout>>
    private lateinit var listOfDays:LiveData<List<DietDay>>
    private lateinit var currentUser: LiveData<User>

    override fun getWorkouts(): LiveData<List<Workout>> {
        listOfWorkouts= workoutDao.getAllWorkouts()
        return listOfWorkouts
    }

    override fun getCurrentUser(): LiveData<User>{
        currentUser = userDao.getCurrentUser(FirebaseAuth.getInstance().currentUser?.uid)
        return currentUser
    }

    override fun getAllDays(): LiveData<List<DietDay>> {
        listOfDays = dayDao.getAllDays()
        return listOfDays
    }

    override suspend fun addDay(day: DietDay) {
        dayDao.insertDay(day)
        val dbRef = FirebaseDatabase.getInstance().getReference("UsersKotlin").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Days").child(day.id)
        dbRef.setValue(day)
    }

    override suspend fun deleteDay(id: String) {
        dayDao.deleteDay(id)
        val dbRef = FirebaseDatabase.getInstance().getReference("UsersKotlin").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Days").child(id)
        dbRef.removeValue()
    }

    override suspend fun deleteData() {
            workoutDao.deleteAllWorkouts()
            exerciseDao.deleteAllExercises()
            userDao.deleteAllUsers()
            dayDao.deleteAllDays()

    }

    override suspend fun deleteWorkout(id: String) {
        workoutDao.deleteWorkout(id)
        val dbRef = FirebaseDatabase.getInstance().getReference("UsersKotlin").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Workouts").child(id)
        dbRef.removeValue()
    }



}