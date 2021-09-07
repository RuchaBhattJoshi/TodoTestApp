package com.example.todotestapp.remote

import com.example.todotestapp.model.TodoItem
import retrofit2.http.GET

interface TodoApi {
    @GET("/todos/")
    suspend fun fetchTodoList(): List<TodoItem>
}