package com.example.todo.data

import android.app.Application
import android.content.ContentValues
import android.database.Cursor
import com.example.todo.data.db.provider.TaskContract
import com.example.todo.presentation.model.Task
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.internal.operators.completable.CompletableFromAction


class DataManagerImpl (private val application: Application) : DataManager {
    override fun clearTasks(): Completable {
        return CompletableFromAction {
            val uri = TaskContract.TaskEntry.CONTENT_URI
            application.contentResolver.delete(uri, null, null)
        }
    }

    override fun deleteTask(id: String): Completable {
        return CompletableFromAction {
            val uri = TaskContract.TaskEntry.CONTENT_URI.buildUpon().appendPath(id).build()
            application.contentResolver.delete(uri, null, null)
        }
    }

    override fun saveTask(task: Task): Completable {
        return CompletableFromAction {
            val contentValues = ContentValues()
            contentValues.put(TaskContract.TaskEntry.COLUMN_TITLE, task.title)
            contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, task.description)
            contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY, task.priority)
            application.contentResolver.insert(TaskContract.TaskEntry.CONTENT_URI, contentValues)
        }
    }

    override fun getTasks(): Flowable<List<Task>> = Flowable.just(
            application.contentResolver.query(TaskContract.TaskEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    TaskContract.TaskEntry.COLUMN_PRIORITY)
    ).map { fromCursorToTasks(it) }

    fun fromCursorToTasks(cursor: Cursor): List<Task> {
        val idIndex = cursor.getColumnIndex(TaskContract.TaskEntry._ID)
        val titleIndex = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TITLE)
        val descriptionIndex = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION)
        val priorityIndex = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_PRIORITY)

        val list = mutableListOf<Task>()
        for (i in 0 until cursor.count) {
            if (!cursor.moveToPosition(i)) {
                break
            }
            val id = cursor.getLong(idIndex)
            val title = cursor.getString(titleIndex)
            val description = cursor.getString(descriptionIndex)
            val priority = cursor.getInt(priorityIndex)
            list.add(Task(id, title, description, priority))
        }
        return list.toList()
    }

}