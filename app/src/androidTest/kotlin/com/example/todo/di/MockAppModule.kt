package com.example.todo.di

import android.app.Application
import com.example.todo.data.DataManager
import com.example.todo.data.DataManagerWithLocalRoom
import com.example.todo.di.module.AppModule

class MockAppModule(val app: Application) : AppModule(app) {
    override fun provideDataManager(): DataManager =
            DataManagerWithLocalRoom(app)
}