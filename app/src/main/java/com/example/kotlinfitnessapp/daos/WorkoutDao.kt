package com.example.kotlinfitnessapp.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinfitnessapp.model.Workout
@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout?)

    @Query("DELETE FROM workout_table WHERE id = :inputId")
    suspend fun deleteWorkout(inputId: String?)

    @Query("SELECT * FROM workout_table ")
    fun getAllWorkouts(): LiveData<List<Workout>>

    @Query("DELETE FROM workout_table")
    suspend fun deleteAllWorkouts()
}