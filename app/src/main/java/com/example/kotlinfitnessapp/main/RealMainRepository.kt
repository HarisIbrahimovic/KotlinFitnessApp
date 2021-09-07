package com.example.kotlinfitnessapp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinfitnessapp.daos.DayDao
import com.example.kotlinfitnessapp.daos.ExerciseDao
import com.example.kotlinfitnessapp.daos.UserDao
import com.example.kotlinfitnessapp.daos.WorkoutDao
import com.example.kotlinfitnessapp.model.DietDay
import com.example.kotlinfitnessapp.model.Exercise
import com.example.kotlinfitnessapp.model.User
import com.example.kotlinfitnessapp.model.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class RealMainRepository @Inject constructor(private val userDao: UserDao,private val exerciseDao: ExerciseDao,private val workoutDao: WorkoutDao,private val dayDao: DayDao) : MainRepository {


    private val auth = FirebaseAuth.getInstance()
    private val userLoggedIn = MutableLiveData<Boolean>()
    override fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userLoggedIn.value = true
                addUser()
            }
        }
    }

    private fun addUser() {
        val id = auth.currentUser!!.uid
        val databaseRef = FirebaseDatabase.getInstance().getReference("UsersKotlin").child(id)
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                GlobalScope.launch(Dispatchers.IO) {
                    userDao.addUser(user)
                    getWorkouts()
                    getExercises()
                    getDays()
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    override fun createUser(
            email: String,
            password: String,
            userName: String,
            weight: Int,
            height: Int
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task->
            if(task.isSuccessful){
                userLoggedIn.value = true
                val fUser = auth.currentUser
                val user = User(fUser!!.uid, height, weight, userName, email, password)
                addToList(user)
                GlobalScope.launch(Dispatchers.IO) {
                    userDao.addUser(user)
                    getWorkouts()
                    getExercises()
                    getDays()
                }
            }
        }
    }

    override fun getUser(): LiveData<Boolean> {
        userLoggedIn.value = auth.currentUser!=null
        return userLoggedIn
    }

    override fun addToList(user: User) {
        val dataBaseReference = FirebaseDatabase.getInstance().getReference("UsersKotlin").child(user.id)
        dataBaseReference.setValue(user)
    }

    override fun getExercises() {
        val dataBaseReference = FirebaseDatabase.getInstance().getReference("UsersKotlin").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Exercises")
        dataBaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val exc = dataSnapshot.getValue(Exercise::class.java)
                    GlobalScope.launch(Dispatchers.IO) {
                        exerciseDao.addExercise(exc)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })

    }

    override fun getWorkouts() {
        val dataBaseReference = FirebaseDatabase.getInstance().getReference("UsersKotlin").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Workout")
        dataBaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val workout = dataSnapshot.getValue(Workout::class.java)
                    GlobalScope.launch(Dispatchers.IO) {
                        workoutDao.insertWorkout(workout)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun getDays() {
        val dataBaseReference = FirebaseDatabase.getInstance().getReference("UsersKotlin").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Days")
        dataBaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val day = dataSnapshot.getValue(DietDay::class.java)
                    GlobalScope.launch(Dispatchers.IO) {
                        dayDao.insertDay(day)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}