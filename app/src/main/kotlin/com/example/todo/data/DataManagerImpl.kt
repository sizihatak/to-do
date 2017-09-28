package com.example.todo.data

import android.app.Application
import android.content.ContentValues
import android.net.Uri
import com.example.todo.data.db.TaskContract
import com.example.todo.presentation.model.Task
import io.reactivex.Single

class DataManagerImpl(private val application: Application) : DataManager {
    override fun saveTask(task: Task): Single<Uri> {
        return Single.fromCallable {
            val contentValues = ContentValues()
            contentValues.put(TaskContract.TaskEntry.COLUMN_TITLE, task.title)
            contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, task.description)
            contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY, task.priority)
            return@fromCallable application.contentResolver.insert(TaskContract.TaskEntry.CONTENT_URI, contentValues)
        }

    }

    override fun getTasks(): Single<Task> = Single.just(Task("", "", 0))
}