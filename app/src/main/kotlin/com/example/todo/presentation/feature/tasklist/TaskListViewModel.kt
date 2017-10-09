package com.example.todo.presentation.feature.tasklist

import android.app.Application
import android.arch.lifecycle.*
import android.view.View
import com.example.todo.data.DataManager
import com.example.todo.presentation.model.Task
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class TaskListViewModel @Inject constructor(application: Application, lifecycle: Lifecycle, private val dataManager: DataManager) : AndroidViewModel(application), LifecycleObserver {

    val onStartAddTaskScreenObserver: PublishSubject<Unit> = PublishSubject.create()
    val onDeleteTaskObserver: PublishSubject<Int> = PublishSubject.create()
    val listTaskObservable: MutableLiveData<List<Task>> = MutableLiveData()
    private val disposable = CompositeDisposable()

    init {
        lifecycle.addObserver(this)
    }

    fun showAddTaskScreen(view: View) {
        onStartAddTaskScreenObserver.onNext(Unit)
    }

    fun deleteTask(position: Int, task: Task) {
        disposable.add(
                dataManager.deleteTask(task.id.toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            onDeleteTaskObserver.onNext(position)
                        }))
    }

    fun getTaskList() {
        disposable.add(dataManager.getTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    listTaskObservable.value = list
                }))
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(){
        getTaskList()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        disposable.clear()
    }
}