package com.example.todotestapp.di

import android.app.Application
import androidx.room.Room
import com.example.todotestapp.BaseApplication
import com.example.todotestapp.local.TodoDao
import com.example.todotestapp.local.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): TodoDatabase =
        Room.databaseBuilder(application, TodoDatabase::class.java, "TodoDatabase")
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providesDao(db: TodoDatabase): TodoDao = db.getDao()


}