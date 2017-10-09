package com.example.todo.presentation.feature.addtask

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.todo.data.DataManager

class AddTaskViewModelFactory(private val application: Application, private val dataManager: DataManager) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        modelClass?.let {
            if (modelClass.isAssignableFrom(AddTaskViewModel::class.java)) {
                return AddTaskViewModel(application, dataManager) as T
            }
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}