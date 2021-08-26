package com.example.kotlinfitnessapp.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_table")
class Exercise(
    @PrimaryKey
    @NonNull
    val id : String = "id",
    val workoutId : String = "workoutId",
    val exerciseName : String = "exsName",
    val weight : Int = 0,
    val reps : Int =0,
    val rest : Int = 0,
    val sets : Int = 0
)

