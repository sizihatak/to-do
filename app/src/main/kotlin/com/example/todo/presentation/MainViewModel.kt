package com.example.todo.presentation

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.view.View
import io.reactivex.Completable
import io.reactivex.CompletableEmitter

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var complit: CompletableEmitter? = null

    val onFabObserver: Completable = Completable.create { e: CompletableEmitter -> complit = e }

    fun showAddTaskScreen(view: View) {
        val onComplete = complit?.onComplete()
    }
}