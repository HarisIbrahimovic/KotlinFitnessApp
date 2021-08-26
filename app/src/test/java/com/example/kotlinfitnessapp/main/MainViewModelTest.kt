package com.example.kotlinfitnessapp.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kotlinfitnessapp.application.CONSTATNTS
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.StringBuilder

class MainViewModelTest{

    private lateinit var viewModel: MainViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel=MainViewModel(FakeMainRepository())
    }

    @Test
    fun checkEmailFormat(){
        assertThat(viewModel.checkEmail("harisgmail.com")).isFalse()
    }

    @Test
    fun emptyFieldsLogin(){
        viewModel.loginUser("","password")
        assertThat(viewModel.getToastMessage().value.equals("Fill in the fields.")).isTrue()
    }

    @Test
    fun emptyFieldsSignUp(){
        viewModel.createUser("","password","name","0","0")
        assertThat(viewModel.getToastMessage().value.equals("Fill in the fields.")).isTrue()
    }

    @Test
    fun passwordTooShort(){
        val password = StringBuilder()
        for(i in 0 until CONSTATNTS.passwordLengthMin-1){
            password.append("c")
        }
        viewModel.createUser("haris@gmail.com",password.toString(),"haris","90","185")
        assertThat(viewModel.getToastMessage().value.equals("Password too short.")).isTrue()
    }

    @Test
    fun userNameLengthMin(){
        val userName = StringBuilder()
        for(i in 0 until CONSTATNTS.nameLengthMin-1){
            userName.append("c")
        }
        viewModel.createUser("haris@gmail.com","password",userName.toString(),"90","185")
        assertThat(viewModel.getToastMessage().value.equals("Username too short.")).isTrue()
    }

    @Test
    fun userNameLengthMax(){
        val userName = StringBuilder()
        for(i in 0 until CONSTATNTS.nameLengthMax+1){
            userName.append("c")
        }
        viewModel.createUser("haris@gmail.com","password",userName.toString(),"90","185")
        assertThat(viewModel.getToastMessage().value.equals("Username too long.")).isTrue()
    }

    @Test
    fun signUpSucces(){
        viewModel.createUser("haris@gmail.com","password","haris","90","185")
        assertThat(viewModel.getToastMessage().value.equals("User created successfully.")).isTrue()
    }

    @Test
    fun checkWeight(){
        viewModel.createUser("haris@gmail.com","password","haris","0","185")
        assertThat(viewModel.getToastMessage().value.equals("Invalid weight.")).isTrue()
    }

    @Test
    fun checkHeight(){
        viewModel.createUser("haris@gmail.com","password","haris","80","0")
        assertThat(viewModel.getToastMessage().value.equals("Invalid height.")).isTrue()
    }

    @Test
    fun checkEmailSignUp(){
        viewModel.createUser("harisail.com","password","haris","80","195")
        assertThat(viewModel.getToastMessage().value.equals("Invalid email.")).isTrue()

    }


}