package com.example.todo.data.db.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskTable(@PrimaryKey
                     @ColumnInfo(name = "id")
                     val id: String,
                     @ColumnInfo(name = "title")
                     val title: String,
                     @ColumnInfo(name = "description")
                     val description: String,
                     @ColumnInfo(name = "priority")
                     val priority: Int)