package com.example.todo.di

import com.example.todo.di.module.ActivityModule
import com.example.todo.di.scope.PerActivity
import com.example.todo.presentation.feature.tasklist.TaskListActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface TaskListComponent : AndroidInjector<TaskListActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<TaskListActivity>() {
        abstract fun plusActivityModule(activityModule: ActivityModule): Builder

        override fun seedInstance(instance: TaskListActivity?) {
            plusActivityModule(ActivityModule(instance!!))
        }
    }
}