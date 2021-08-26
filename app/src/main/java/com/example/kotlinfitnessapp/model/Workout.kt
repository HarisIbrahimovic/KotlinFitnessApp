package com.example.kotlinfitnessapp.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_table")
class Workout(
    @PrimaryKey
    @NonNull
    val id : String ="id",
    val name : String = "name"
)