package com.example.kotlinfitnessapp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinfitnessapp.model.User

class FakeMainRepository : MainRepository{

    val listOfUsers = ArrayList<User>()
    private var userLoggedIn = MutableLiveData<Boolean>()

    override fun loginUser(email: String, password: String) {
        userLoggedIn.value = listOfUsers[0].password == password && listOfUsers[0].email == email
    }

    override fun createUser(
        email: String,
        password: String,
        userName: String,
        weight: Int,
        height: Int
    ) {
        val user = User("1",height,weight,userName,email,password)
        listOfUsers.add(user)
        userLoggedIn.value = true
    }

    override fun getUser(): LiveData<Boolean> = userLoggedIn

    override suspend fun addToList(user: User) {
        listOfUsers.add(user)
    }

    override fun getExercises() {

    }

    override fun getWorkouts() {

    }

    override fun getDays() {

    }


}