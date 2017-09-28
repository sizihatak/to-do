package com.example.todo.presentation.addtask

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.todo.ToDoApplication

class AddTaskViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        modelClass?.let {
            if (modelClass.isAssignableFrom(AddTaskViewModel::class.java)) {
                return AddTaskViewModel(application, (application as ToDoApplication).dataManager) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}