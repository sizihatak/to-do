package com.example.todo.di.module

import android.app.Application
import com.example.todo.data.DataManager
import com.example.todo.data.DataManagerWithRoomImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AppModule(private val app: Application) {

    @Provides
    @Singleton
    internal fun appApplication(): Application = app

    @Provides
    @Singleton
    open internal fun provideDataManager(): DataManager = DataManagerWithRoomImpl(app)

}