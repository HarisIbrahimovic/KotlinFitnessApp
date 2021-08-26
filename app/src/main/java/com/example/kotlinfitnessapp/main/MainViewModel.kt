package com.example.kotlinfitnessapp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinfitnessapp.application.CONSTATNTS
import dagger.hilt.android.lifecycle.HiltViewModel

import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor( private val mainRepository: MainRepository):ViewModel() {

    private val toastMessage = MutableLiveData<String>()
    private var loggedInState : LiveData<Boolean>
    private val stateNum = MutableLiveData<Int>()

    fun getLoggedInState()=loggedInState
    fun getToastMessage() = toastMessage
    fun getStateNum() = stateNum

    fun setToastMessage(mess: String){
        toastMessage.value=mess
    }
    fun setStateNum(num:Int){
        stateNum.value=num
    }

    init {
        toastMessage.value="none"
        loggedInState = mainRepository.getUser()
    }

    fun loginUser(email: String, password: String) {
        if(email.isEmpty()||password.isEmpty()){
            toastMessage.value="Fill in the fields."
            return
        }
        mainRepository.loginUser(email,password)

    }

    fun createUser(
        email: String,
        password: String,
        username: String,
        weight: String,
        height: String
    ){
        if(email.isEmpty()||password.isEmpty()||username.isEmpty()||weight.isEmpty()||height.isEmpty()){
            toastMessage.value="Fill in the fields."
            return
        }
        val nHeight = Integer.parseInt(height)
        val nWeight = Integer.parseInt(weight)
        if(username.length<CONSTATNTS.nameLengthMin){
            toastMessage.value = "Username too short."
            return
        }
        if(username.length>CONSTATNTS.nameLengthMax){
            toastMessage.value = "Username too long."
            return
        }
        if(password.length<CONSTATNTS.passwordLengthMin){
            toastMessage.value = "Password too short."
            return
        }
        if(nWeight>CONSTATNTS.maxWeight||nWeight<CONSTATNTS.minWeight){
            toastMessage.value = "Invalid weight."
            return
        }
        if(nHeight>CONSTATNTS.maxHeight||nHeight<CONSTATNTS.minHeight){
            toastMessage.value = "Invalid height."
            return
        }
        if(!checkEmail(email)){
            toastMessage.value = "Invalid email."
            return
        }
        toastMessage.value = "User created successfully."

        mainRepository.createUser(email,password,username,nWeight,nHeight)

    }

    fun chosePath(
        email: String,
        password: String,
        username: String,
        weight: String,
        height: String,
        buttonText: String
    ){
        when(buttonText){
            "Login"->loginUser(email,password)
            "Sign Up"->createUser(email, password, username, weight, height)
        }
    }

    fun checkEmail(email: String): Boolean {
        val emailPattern = Pattern
            .compile(
                "[a-zA-Z0-9+._%-+]{1,256}" + "@"
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+"
            )
        return emailPattern.matcher(email).matches()
    }

}