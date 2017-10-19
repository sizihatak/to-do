package com.example.todo

import android.app.Application
import com.example.todo.di.AppComponent
import com.example.todo.di.DaggerAppComponent
import com.example.todo.di.module.AppModule

open class ToDoApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        createComponent().also{appComponent = it}.inject(this)
    }

    open protected fun createComponent(): AppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
}