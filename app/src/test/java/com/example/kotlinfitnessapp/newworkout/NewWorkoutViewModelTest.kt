package com.example.kotlinfitnessapp.newworkout

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kotlinfitnessapp.application.CONSTATNTS
import com.google.common.truth.Truth.assertThat

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.StringBuilder

class NewWorkoutViewModelTest{

    private lateinit var viewModel: NewWorkoutViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        viewModel = NewWorkoutViewModel(FakeNewWorkoutRepository())
    }

    @Test
    fun addWorkoutWithEmptyName(){
        viewModel.addWorkout("")
        assertThat(viewModel.getToastMessage().value.equals("Add workout name.")).isTrue()
    }

    @Test
    fun addWorkoutNameTooLong(){
        val name = StringBuilder()
        for(i in 0 until CONSTATNTS.maxWorkoutNameLength+1)name.append("c")
        viewModel.addWorkout(name.toString())
        assertThat(viewModel.getToastMessage().value=="Workout name too long.").isTrue()
    }

    @Test
    fun exerciseMissingInfo(){
        viewModel.addExercise("","1","1","120","80")
        assertThat(viewModel.getToastMessage().value=="Add exercise info.").isTrue()
    }

    @Test
    fun addExerciseNameTooLong(){
        val name = StringBuilder()
        for(i in 0 until CONSTATNTS.maxExerciseNameLength+1)name.append("c")
        viewModel.addExercise(name.toString(),"1","1","120","80")
        assertThat(viewModel.getToastMessage().value=="Exercise name too long.").isTrue()
    }

    @Test
    fun addExerciseSuccess(){
        viewModel.setWorkoutId()
        viewModel.addExercise("bench","1","1","120","80")
        assertThat(viewModel.getToastMessage().value=="Exercise added.").isTrue()
    }

    @Test
    fun checkValueAdderSets(){
        viewModel.addValue("10",5,"Sets")
        assertThat(viewModel.getSetsValue().value=="15").isTrue()
    }

    @Test
    fun checkValueAdderWeight(){
        viewModel.addValue("10",5,"Weight")
        assertThat(viewModel.getWeightValue().value=="15").isTrue()
    }



}