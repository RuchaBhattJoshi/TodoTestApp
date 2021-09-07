package com.example.todotestapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName="todoTable")
data class TodoItem(

    @ColumnInfo(name = "id")
    @SerializedName("id")
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,

    @ColumnInfo(name = "userId")
    @SerializedName("userId")
    val userId: Int,

    @ColumnInfo(name = "completed")
    @SerializedName("completed")
    val completed: Boolean,

    @ColumnInfo(name = "isFavorite")
    val isFavorite:Boolean
)