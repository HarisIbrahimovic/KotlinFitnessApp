package com.example.kotlinfitnessapp.exercises

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExerciseViewModelTest{

    private lateinit var viewModel: ExerciseViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        viewModel= ExerciseViewModel(FakeExerciseRepository())
    }

    @Test
    fun emptyFields(){
        viewModel.updateExercise("1","2","","1","1","1","1")
        assertThat(viewModel.getToastMessage().value=="Fill in the fields.").isTrue()
    }

    @Test
    fun successfulUpdate(){
        viewModel.updateExercise("1","2","name","1","1","1","1")
        assertThat(viewModel.getToastMessage().value=="Done").isTrue()
    }



}