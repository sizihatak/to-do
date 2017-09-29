package com.example.todo.presentation.feature.tasklist

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.todo.ToDoApplication

class TaskListViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        modelClass?.let {
            if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
                return TaskListViewModel(application, (application as ToDoApplication).dataManager) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}