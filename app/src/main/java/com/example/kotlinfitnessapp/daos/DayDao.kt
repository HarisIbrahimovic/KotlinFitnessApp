package com.example.kotlinfitnessapp.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinfitnessapp.model.DietDay

@Dao
interface DayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(dietDay: DietDay?)

    @Query("DELETE FROM day_table")
    suspend fun deleteAllDays()

    @Query("DELETE FROM day_table WHERE id = :inputId")
    suspend fun deleteDay(inputId: String?)

    @Query("SELECT * FROM day_table ")
    fun getAllDays(): LiveData<List<DietDay>>
}