package com.example.todo.di

import com.example.todo.ToDoApplication
import com.example.todo.di.module.AppModule
import com.example.todo.di.module.AppScBuildersModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AppScBuildersModule::class])
interface AppComponent {
    fun inject(app: ToDoApplication)
}