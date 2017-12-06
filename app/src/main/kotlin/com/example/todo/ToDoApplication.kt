package com.example.todo

import android.app.Activity
import android.app.Application
import com.example.todo.di.DaggerAppComponent
import com.example.todo.di.module.AppModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

open class ToDoApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
                .inject(this)
    }
}