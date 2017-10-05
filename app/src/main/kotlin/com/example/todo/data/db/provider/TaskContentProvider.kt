package com.example.todo.data.db.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.net.Uri

class TaskContentProvider : ContentProvider() {
    companion object {

        private const val TASKS = 100
        private const val TASK_WITH_ID = 101

        @JvmField
        val uriMatcher: UriMatcher = buildUriMatcher()

        private fun buildUriMatcher(): UriMatcher {
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
            uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS, TASKS)
            uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS + "/#", TASK_WITH_ID)
            return uriMatcher
        }
    }

    lateinit var taskDbHelper: TaskDbHelper

    override fun onCreate(): Boolean {
        TaskContract.AUTHORITY
        taskDbHelper = TaskDbHelper(context)
        return true
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        val db = taskDbHelper.writableDatabase

        val match = uriMatcher.match(uri)

        val returnUri: Uri?

        when (match) {
            TASKS -> {
                val id = db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values)
                if (id > 0)
                    returnUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI, id)
                else
                    throw SQLException("Failed to insert row into $uri")
            }
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }
        context.contentResolver.notifyChange(uri, null)
        return returnUri
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        val db = taskDbHelper.readableDatabase

        val match = uriMatcher.match(uri)

        val result: Cursor

        when (match) {
            TASKS -> {
                result = db.query(TaskContract.TaskEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                )
            }
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }
        result.setNotificationUri(context.contentResolver, uri)
        return result
    }

    override fun update(p0: Uri?, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = taskDbHelper.writableDatabase
        val match = uriMatcher.match(uri)
        var taskDelete = 0
        uri?.let {
            when (match) {
                TASK_WITH_ID -> {
                    val id = uri.pathSegments[1]
                    taskDelete = db.delete(TaskContract.TaskEntry.TABLE_NAME,
                            "${TaskContract.TaskEntry._ID}=?", arrayOf(id))
                }
                else -> throw UnsupportedOperationException("Unknown uri: $uri")
            }
        }
        if (taskDelete != 0) {
            context.contentResolver.notifyChange(uri, null)
        }
        return taskDelete
    }

    override fun getType(p0: Uri?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}