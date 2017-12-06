package com.example.todo.di

import com.example.todo.di.module.ActivityModule
import com.example.todo.di.scope.PerActivity
import com.example.todo.presentation.feature.addtask.AddTaskActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface AddTaskComponent : AndroidInjector<AddTaskActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<AddTaskActivity>() {
        abstract fun plusActivityModule(activityModule: ActivityModule): Builder

        override fun seedInstance(instance: AddTaskActivity?) {
            plusActivityModule(ActivityModule(instance!!))
        }
    }
}