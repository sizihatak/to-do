package com.example.todo.data

import android.net.Uri
import com.example.todo.presentation.model.Task
import io.reactivex.Single

interface DataManager {
    fun saveTask(task: Task):Single<Uri>
    fun getTasks(): Single<Task>
}