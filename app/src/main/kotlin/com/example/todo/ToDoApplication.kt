package com.example.todo

import android.app.Application
import com.example.todo.data.DataManager
import com.example.todo.data.DataManagerImpl

class ToDoApplication : Application() {
    lateinit var dataManager: DataManager

    override fun onCreate() {
        super.onCreate()
        dataManager = DataManagerImpl(this)
    }
}