package com.example.todotestapp.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.todotestapp.model.TodoItem
import com.example.todotestapp.repository.TodoRepository

class TodoSource(
    private val todoRepository: TodoRepository
) : PagingSource<Int, TodoItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TodoItem> {

        val nextPage = params.key ?: 1
        return try {
            val todoResponse = todoRepository.getTodos((nextPage-1)*10)
            LoadResult.Page(
                data = todoResponse,
                prevKey =  if (nextPage == 1) null else nextPage - 1,
                nextKey =  if(todoResponse.isEmpty()) null else nextPage + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, TodoItem>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

