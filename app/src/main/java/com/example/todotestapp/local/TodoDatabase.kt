package com.example.todotestapp.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todotestapp.model.TodoItem

@Database(entities = [TodoItem::class],version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun getDao():TodoDao
}