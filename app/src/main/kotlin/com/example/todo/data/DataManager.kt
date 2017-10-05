package com.example.todo.data

import com.example.todo.presentation.model.Task
import io.reactivex.Completable
import io.reactivex.Flowable

interface DataManager {
    fun saveTask(task: Task): Completable
    fun getTasks(): Flowable<List<Task>>
    fun deleteTask(id: String): Completable
}