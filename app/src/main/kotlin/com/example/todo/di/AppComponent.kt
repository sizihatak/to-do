package com.example.todo.di

import android.app.Application
import com.example.todo.ToDoApplication
import com.example.todo.data.DataManager
import com.example.todo.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(app: ToDoApplication)

    fun getDataManager(): DataManager
    fun getApplication(): Application
}