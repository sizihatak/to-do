package com.example.todo.di.module

import android.app.Activity
import com.example.todo.di.AddTaskComponent
import com.example.todo.di.TaskListComponent
import com.example.todo.presentation.feature.addtask.AddTaskActivity
import com.example.todo.presentation.feature.tasklist.TaskListActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [TaskListComponent::class, AddTaskComponent::class])
abstract class AppScBuildersModule {

    @Binds
    @IntoMap
    @ActivityKey(TaskListActivity::class)
    abstract fun bindTaskListActivityInjectorFactory(taskListComponent: TaskListComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @IntoMap
    @ActivityKey(AddTaskActivity::class)
    abstract fun bindAddTaskActivityInjectorFactory(addTaskComponent: AddTaskComponent.Builder): AndroidInjector.Factory<out Activity>

}