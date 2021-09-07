package com.example.todotestapp.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todotestapp.model.TodoItem

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(todoItems: List<TodoItem>)

    @Query("SELECT * FROM todoTable ORDER BY id LIMIT 10 OFFSET :offset")
    suspend fun getAllTodos(offset:Int): List<TodoItem>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(todoItem: TodoItem)

    @Query("SELECT * FROM todoTable where isFavorite = 1")
    fun getAllFavorites():LiveData<List<TodoItem>>

}