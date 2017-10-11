package com.example.todo.presentation.feature

import com.example.todo.ToDoApplication
import mock

class ToDoApplicationTest: ToDoApplication() {
    override fun onCreate() {
        super.onCreate()
        appComponent = mock()
    }
}