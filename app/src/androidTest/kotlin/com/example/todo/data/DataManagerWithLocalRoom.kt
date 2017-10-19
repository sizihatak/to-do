package com.example.todo.data

import android.app.Application
import android.arch.persistence.room.Room
import com.example.todo.data.db.room.TaskDao
import com.example.todo.data.db.room.TasksDataBase

class DataManagerWithLocalRoom(application: Application) : DataManagerWithRoomImpl(application) {
    override val taskDao: TaskDao = Room.inMemoryDatabaseBuilder(application,
            TasksDataBase::class.java).allowMainThreadQueries().build().taskDao()
}