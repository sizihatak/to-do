package com.example.todo.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
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

    override fun insert(uri: Uri?, p1: ContentValues?): Uri? {
        val db = taskDbHelper.writableDatabase

        val match = uriMatcher.match(uri)

        return null
    }

    override fun query(p0: Uri?, p1: Array<out String>?, p2: String?, p3: Array<out String>?, p4: String?): Cursor {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(p0: Uri?, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(p0: Uri?, p1: String?, p2: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(p0: Uri?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}