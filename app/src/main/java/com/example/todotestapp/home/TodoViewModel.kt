package com.example.todotestapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.todotestapp.utils.TodoSource
import com.example.todotestapp.model.TodoItem
import com.example.todotestapp.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel
@Inject
constructor(private val repository: TodoRepository) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.makeApiCall()
        }
    }

    fun getTodos() = Pager(PagingConfig(10,enablePlaceholders = true,maxSize = 200)){
        TodoSource(repository)
    }.flow

    fun update(todoItem: TodoItem){
        viewModelScope.launch {
            repository.update(todoItem)
        }
    }

}