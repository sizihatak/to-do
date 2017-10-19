package com.example.todo

import com.example.todo.di.AppComponent
import com.example.todo.di.DaggerAppComponent
import com.example.todo.di.MockAppModule

class ToDoApplicationTest : ToDoApplication() {
    override fun createComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(MockAppModule(this))
                .build()
    }
}
