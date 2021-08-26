package com.example.kotlinfitnessapp.menu

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MenuViewModelTest{
    private lateinit var viewModel: MenuViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        viewModel = MenuViewModel(FakeMenuRepository())
    }

    @Test
    fun addWithEmptyFields(){
        viewModel.addDay("test","","150","180")
        assertThat(viewModel.getToastMessage().value=="Fill in the fields.").isTrue()
    }

    @Test
    fun addDaySuccess(){
        viewModel.addDay("test","180","150","180")
        assertThat(viewModel.getToastMessage().value=="Day added.").isTrue()
    }
}