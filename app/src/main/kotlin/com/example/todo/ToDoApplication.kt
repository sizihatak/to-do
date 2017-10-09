package com.example.todo

import android.app.Application
import com.example.todo.di.AppComponent
import com.example.todo.di.DaggerAppComponent
import com.example.todo.di.module.AppModule

class ToDoApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        appComponent.inject(this)
    }
}