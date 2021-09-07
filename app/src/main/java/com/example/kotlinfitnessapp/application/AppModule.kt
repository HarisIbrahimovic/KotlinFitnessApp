package com.example.kotlinfitnessapp.application


import android.content.Context
import androidx.room.Room
import com.example.kotlinfitnessapp.daos.DayDao
import com.example.kotlinfitnessapp.daos.ExerciseDao
import com.example.kotlinfitnessapp.daos.UserDao
import com.example.kotlinfitnessapp.daos.WorkoutDao
import com.example.kotlinfitnessapp.exercises.ExerciseRepository
import com.example.kotlinfitnessapp.exercises.RealExerciseRepository
import com.example.kotlinfitnessapp.main.MainRepository
import com.example.kotlinfitnessapp.main.RealMainRepository
import com.example.kotlinfitnessapp.menu.MenuRepository
import com.example.kotlinfitnessapp.menu.RealMenuRepository
import com.example.kotlinfitnessapp.newworkout.NewWorkoutRepository
import com.example.kotlinfitnessapp.newworkout.RealNewWorkoutRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object  AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "FitnessDatabase"
        ).build()
    @Singleton
    @Provides
    fun provideMainRepository(userDao: UserDao,exerciseDao: ExerciseDao,workoutDao: WorkoutDao,dayDao: DayDao): MainRepository = RealMainRepository(userDao,exerciseDao,workoutDao,dayDao)
    @Singleton
    @Provides
    fun provideNewWorkoutRepository(workoutDao: WorkoutDao,exerciseDao: ExerciseDao):NewWorkoutRepository = RealNewWorkoutRepository(workoutDao,exerciseDao)
    @Singleton
    @Provides
    fun provideExerciseRepository(workoutDao: WorkoutDao,exerciseDao: ExerciseDao):ExerciseRepository = RealExerciseRepository(workoutDao,exerciseDao)
    @Singleton
    @Provides
    fun provideMenuRepository(workoutDao: WorkoutDao,userDao: UserDao,dayDao: DayDao,exerciseDao: ExerciseDao): MenuRepository = RealMenuRepository(workoutDao,userDao,dayDao,exerciseDao)
    @Provides
    fun providesWorkoutDao(appDatabase: AppDatabase): WorkoutDao = appDatabase.workoutDao()
    @Provides
    fun providesExerciseDao(appDatabase: AppDatabase): ExerciseDao = appDatabase.exerciseDao()
    @Provides
    fun providesUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()
    @Provides
    fun providesDayDao(appDatabase: AppDatabase): DayDao = appDatabase.dayDao()
}