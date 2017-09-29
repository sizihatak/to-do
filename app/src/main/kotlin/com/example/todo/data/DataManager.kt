package com.example.todo.data

import com.example.todo.presentation.model.Task
import io.reactivex.Completable
import io.reactivex.Single

interface DataManager {
    fun saveTask(task: Task): Completable
    fun getTasks(): Single<List<Task>>
}