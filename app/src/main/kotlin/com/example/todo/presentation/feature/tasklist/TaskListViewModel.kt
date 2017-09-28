package com.example.todo.presentation.feature.tasklist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.view.View
import io.reactivex.subjects.PublishSubject

class TaskListViewModel(application: Application) : AndroidViewModel(application) {

    val onStartAddTaskScreenObserver: PublishSubject<Unit> = PublishSubject.create()

    fun showAddTaskScreen(view: View) {
        onStartAddTaskScreenObserver.onNext(Unit)
    }
}