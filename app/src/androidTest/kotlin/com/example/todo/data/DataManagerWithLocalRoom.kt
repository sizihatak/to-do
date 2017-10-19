package com.example.todo.data

import android.app.Application
import android.content.Context
import com.example.todo.data.db.room.TaskDao
import com.example.todo.data.db.room.TasksDataBase

class DataManagerWithLocalRoom(context: Context) : DataManagerWithRoomImpl(context) {
    override val taskDao: TaskDao = TasksDataBase.getLocalInstance(context).taskDao()
}