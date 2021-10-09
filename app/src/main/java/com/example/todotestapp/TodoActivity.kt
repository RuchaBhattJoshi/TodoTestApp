package com.example.todotestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.todotestapp.ui.theme.DeepBlue
import com.example.todotestapp.favorite.Favorite
import com.example.todotestapp.ui.theme.TodoTestAppTheme
import com.example.todotestapp.ui.theme.topAppBarBackgroundColor
import com.example.todotestapp.ui.theme.topAppBarContentColor
import com.example.todotestapp.utils.Screen
import com.steve.bottomnavigationcompose.Home
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TodoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoTestAppTheme(true) {
                Surface(color = DeepBlue) {
                    BottomNavigation()
                }
            }

        }
    }
}


@Composable
fun BottomNavigation() {
    val items = listOf(Screen.Home, Screen.Favorite)
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name), color = MaterialTheme.colors.topAppBarContentColor)
                },
            backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor)
        },
        bottomBar = {
            BottomNavigation {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }

            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { Home() }
            composable(Screen.Favorite.route) { Favorite() }
        }
    }
}


