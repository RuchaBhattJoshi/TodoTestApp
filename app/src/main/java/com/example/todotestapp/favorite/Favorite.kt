package com.example.todotestapp.favorite

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todotestapp.model.TodoItem
import com.example.todotestapp.utils.Resource
import java.util.*

@Composable
fun Favorite(favoriteViewModel: FavoriteViewModel = hiltViewModel())
{
    val response = favoriteViewModel.getAllFavorites().observeAsState()
    RecyclerView(response)
}

@Composable
fun RecyclerView(todos: State<Resource<List<TodoItem>>?>)
{
    LazyColumn {
        when (val items = todos.value) {
            is Resource.Success -> {
                if (items.data != null && items.data.isNotEmpty()) {
                    items(items.data) { todo ->
                        EachRow(todo = todo)
                    }
                } else {
                    //NoData()
                    Log.d("No Favorite", "No Favorite Data")
                }
            }
            else -> {

            }
        }


    }

}

@Composable
fun EachRow(todo: TodoItem)
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

        Column {
            Text(
                text = todo.title.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                fontWeight = FontWeight.ExtraBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(5.dp)
                    .width(200.dp)
            )
            Text(
                text = todo.completed.toString(),
                fontWeight = FontWeight.ExtraBold,
                color = if (todo.completed.toString() == "true") Color.Green else Color.Red,
                modifier = Modifier.padding(5.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))


    }

}


@Composable
fun NoData() {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No Favorites")
    }

}