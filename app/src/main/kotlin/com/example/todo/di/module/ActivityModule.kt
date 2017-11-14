package com.example.todo.di.module

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import com.example.todo.data.DataManager
import com.example.todo.di.scope.PerActivity
import com.example.todo.presentation.feature.addtask.AddTaskViewModel
import com.example.todo.presentation.feature.addtask.AddTaskViewModelFactory
import com.example.todo.presentation.feature.tasklist.TaskListViewModel
import com.example.todo.presentation.feature.tasklist.TaskListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @PerActivity
    internal fun provideActivity(): Activity = activity


    @Provides
    @PerActivity
    internal fun provideTaskListModelView(application: Application, dataManager: DataManager): TaskListViewModel {
        val factory = TaskListViewModelFactory(application, dataManager, activity.lifecycle)
        return createViewModel(factory, TaskListViewModel::class.java)
    }

    @Provides
    @PerActivity
    internal fun provideAddTaskModelView(application: Application, dataManager: DataManager): AddTaskViewModel {
        val factory = AddTaskViewModelFactory(application, dataManager)
        return createViewModel(factory, AddTaskViewModel::class.java)
    }

    private fun <T : ViewModel> createViewModel(factory: ViewModelProvider.Factory, modelClass: Class<T>): T =
            ViewModelProviders.of(activity, factory).get(modelClass)
}