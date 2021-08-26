package com.example.kotlinfitnessapp.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinfitnessapp.model.Exercise

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addExercise(exercise: Exercise?)

    @Query("DELETE FROM exercise_table WHERE id = :inputId")
    fun deleteExs(inputId: String?)

    @Query("DELETE FROM exercise_table ")
    fun deleteAllExercises()

    @Query("SELECT * FROM exercise_table WHERE workoutId = :inputId")
    fun getExerciseList(inputId: String?): LiveData<List<Exercise>>
}