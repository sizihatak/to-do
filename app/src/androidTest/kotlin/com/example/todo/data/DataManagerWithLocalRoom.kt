package com.example.todo.data

import android.app.Application
import android.content.Context
import com.example.todo.data.db.room.TaskDao
import com.example.todo.data.db.room.TasksDataBase

class DataManagerWithLocalRoom(context: Context) : DataManagerWithRoomImpl(context) {
    val dataBase = TasksDataBase.getLocalInstance(context)
    override val taskDao: TaskDao = dataBase.taskDao()
}