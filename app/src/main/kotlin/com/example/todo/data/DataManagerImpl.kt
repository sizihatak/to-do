package com.example.todo.data

import android.app.Application
import android.content.ContentValues
import android.database.Cursor
import com.example.todo.data.db.TaskContract
import com.example.todo.presentation.model.Task
import io.reactivex.Completable
import io.reactivex.Single


class DataManagerImpl(private val application: Application) : DataManager {
    override fun saveTask(task: Task): Completable {
        return Completable.fromCallable {
            val contentValues = ContentValues()
            contentValues.put(TaskContract.TaskEntry.COLUMN_TITLE, task.title)
            contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, task.description)
            contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY, task.priority)
            return@fromCallable application.contentResolver.insert(TaskContract.TaskEntry.CONTENT_URI, contentValues)
        }
    }

    override fun getTasks(): Single<List<Task>> = Single.just(
            application.contentResolver.query(TaskContract.TaskEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    TaskContract.TaskEntry.COLUMN_PRIORITY)
    ).map { transform(it) }

    fun transform(cursor: Cursor): List<Task> {
        val idIndex = cursor.getColumnIndex(TaskContract.TaskEntry._ID)
        val titleIndex = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TITLE)
        val descriptionIndex = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION)
        val priorityIndex = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_PRIORITY)

        val list = mutableListOf<Task>()
        for (i in 0 until cursor.count) {
            if (!cursor.moveToPosition(i)) {
                break
            }
            val title = cursor.getString(titleIndex)
            val description = cursor.getString(descriptionIndex)
            val priority = cursor.getInt(priorityIndex)
            list.add(Task(title, description, priority))
        }
        return list.toList()
    }

}