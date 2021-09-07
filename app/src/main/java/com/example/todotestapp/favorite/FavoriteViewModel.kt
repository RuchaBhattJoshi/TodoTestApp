package com.example.todotestapp.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todotestapp.model.TodoItem
import com.example.todotestapp.repository.TodoRepository
import com.example.todotestapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel
@Inject
constructor(private val repository: TodoRepository) : ViewModel() {
    fun getAllFavorites(): LiveData<Resource<List<TodoItem>>> = repository.getAllFavorites()
}