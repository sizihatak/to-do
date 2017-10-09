package com.example.todo.di.module

import android.app.Application
import com.example.todo.data.DataManager
import com.example.todo.data.DataManagerWithRoomImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    internal fun appApplication(): Application = app

    @Provides
    @Singleton
    internal fun provideDataManager(dataManager: DataManagerWithRoomImpl): DataManager = dataManager

}