package com.example.kotlinfitnessapp.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class User(
    @PrimaryKey
    @NonNull
    val id:String ="test",
    val height: Int = 0,
    val weight: Int = 0 ,
    val username: String = "Name",
    val email: String = "email",
    val password:String = "Password"
    )