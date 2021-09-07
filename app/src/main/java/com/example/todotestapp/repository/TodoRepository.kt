package com.example.todotestapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.todotestapp.local.TodoDao
import com.example.todotestapp.model.TodoItem
import com.example.todotestapp.remote.TodoApi
import com.example.todotestapp.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoRepository
@Inject
constructor(
    private val dao: TodoDao,
    private val todoapi: TodoApi
) {
    //local
    suspend fun getTodos(offset:Int): List<TodoItem> {
        return dao.getAllTodos(offset)
    }

    //network
     suspend fun makeApiCall(){
            try {
                val response = todoapi.fetchTodoList()
                dao.insertAll(response)
            } catch (e: Exception) {
                Log.d("api call fail", "fail")

            }
    }


    suspend fun update(todoItem: TodoItem) {
        dao.updateFavorite(todoItem)
    }

    fun getAllFavorites(): LiveData<Resource<List<TodoItem>>> {

        return dao.getAllFavorites().map {
            if (it.isEmpty()) {
                Resource.Error("No favorites")
            } else {
                Resource.Success(it)
            }
        }

    }


}