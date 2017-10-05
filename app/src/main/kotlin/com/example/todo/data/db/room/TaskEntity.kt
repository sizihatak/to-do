package com.example.todo.data.db.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(@PrimaryKey(autoGenerate = true)
                      @ColumnInfo(name = "id")
                      val id: Long?,
                      @ColumnInfo(name = "title")
                      val title: String,
                      @ColumnInfo(name = "description")
                      val description: String,
                      @ColumnInfo(name = "priority")
                      val priority: Int)