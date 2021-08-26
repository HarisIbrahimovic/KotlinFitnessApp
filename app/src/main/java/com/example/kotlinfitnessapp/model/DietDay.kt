package com.example.kotlinfitnessapp.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_table")
class DietDay(
    @PrimaryKey
    @NonNull
    val id : String = "id",
    val protein : Int = 0,
    val carbs : Int = 0,
    val calories : Int = 0,
    val fats : Int = 0
)