package com.steve.bottomnavigationcompose


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.todotestapp.home.TodoViewModel
import com.example.todotestapp.model.TodoItem
import com.example.todotestapp.utils.ErrorItem
import com.example.todotestapp.utils.LoadingItem
import com.example.todotestapp.utils.LoadingView
import java.util.*


@Composable
fun Home(todoViewModel: TodoViewModel = hiltViewModel())
{
    val response = todoViewModel.getTodos().collectAsLazyPagingItems()
    RecyclerView(response)
}



@Composable
fun RecyclerView(todos: LazyPagingItems<TodoItem>,todoViewModel: TodoViewModel = hiltViewModel())
{
        LazyColumn {
            items(todos) { todo ->
                if (todo != null) {
                    EachRow(todo = todo){
                        todoViewModel.update(this)
                        todos.refresh()
                    }
                }
            }

            todos.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem() }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val e = todos.loadState.refresh as LoadState.Error
                        item {
                            ErrorItem(
                                message = e.error.localizedMessage!!,
                                modifier = Modifier.fillParentMaxSize(),
                                onClickRetry = { retry() }
                            )
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val e = todos.loadState.append as LoadState.Error
                        item {
                            ErrorItem(
                                message = e.error.localizedMessage!!,
                                onClickRetry = { retry() }
                            )
                        }
                    }
                }
            }

        }

}


@Composable
fun EachRow(todo: TodoItem,onUpdate:TodoItem.()-> Unit)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.LightGray)
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {

        Surface(
            shape = CircleShape,
            modifier = Modifier.size(50.dp),
            color = Color.Gray
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
               //Text(text = todo.userId.toString(), fontWeight = FontWeight.ExtraBold)
                Text(text = todo.id.toString(), fontWeight = FontWeight.ExtraBold)
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column{
            Text(text = todo.title.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }, fontWeight = FontWeight.ExtraBold,maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(5.dp).width(200.dp))
            Text(text = todo.completed.toString(), fontWeight = FontWeight.ExtraBold,color = if(todo.completed.toString() == "true") Color.Green else Color.Red,modifier = Modifier.padding(5.dp))
        }

        Spacer(modifier = Modifier.width(8.dp))

        IconToggleButton(
            modifier = Modifier
                .padding(6.dp)
                .size(32.dp),
            checked = todo.isFavorite,
            onCheckedChange = {
                onUpdate(todo.copy(isFavorite = !todo.isFavorite))
            }
        ) {
            if (todo.isFavorite) {
                Icon(
                    Icons.Outlined.Favorite,
                    contentDescription = "like"
                )
            } else {
                Icon(
                    Icons.TwoTone.Favorite,
                    contentDescription = "dislike"
                )
            }
        }

    }

}



