package com.example.todo.data.db

import android.net.Uri

class TaskContract {

    companion object {
        const val AUTHORITY: String = "com.example.todo"
        @JvmField
        val BASE_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY")

        const val PATH_TASKS = "tasks"
    }

    class TaskEntry {
        companion object {
            @JvmField
            val CONTENT_URI: Uri = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build()

            const val TABLE_NAME = "tasks"

            const val _ID = "_id"
            const val COLUMN_TITLE = "title"
            const val COLUMN_DESCRIPTION = "description"
            const val COLUMN_PRIORITY = "priority"
            const val _COUNT = "_count"
        }
    }
}