package com.example.todotestapp.utils

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.todotestapp.R

sealed class Screen (var route: String, @StringRes val resourceId: Int, var icon: ImageVector) {
    object Home : Screen("home", R.string.home, Icons.Default.Home)
    object Favorite : Screen("favorite", R.string.favorite, Icons.Default.Favorite )
}
