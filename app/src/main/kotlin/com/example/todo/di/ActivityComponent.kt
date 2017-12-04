package com.example.todo.di

import com.example.todo.di.module.ActivityModule
import com.example.todo.di.scope.PerActivity
import com.example.todo.presentation.feature.addtask.AddTaskActivity
import com.example.todo.presentation.feature.tasklist.TaskListActivity
import dagger.Component


@PerActivity
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: TaskListActivity)
    fun inject(activity: AddTaskActivity)
}