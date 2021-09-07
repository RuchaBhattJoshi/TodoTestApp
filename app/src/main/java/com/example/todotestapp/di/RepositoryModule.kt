package com.example.todotestapp.di

import com.example.todotestapp.repository.TodoRepository
import com.example.todotestapp.local.TodoDao
import com.example.todotestapp.remote.TodoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepository(dao:TodoDao,todoApi: TodoApi): TodoRepository = TodoRepository(dao,todoApi)
}