package com.example.todo.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class TaskDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {
    companion object {
        const val DATABASE_NAME = "tasks.db"
        const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        BaseColumns._ID
        val CREATE_TABLE = "CREATE TABLE ${TaskContract.TaskEntry.TABLE_NAME} (" +
                "${TaskContract.TaskEntry._ID} INTEGER PRIMARY KEY, " +
                "${TaskContract.TaskEntry.COLUMN_TITLE} TEXT NOT NULL, " +
                "${TaskContract.TaskEntry.COLUMN_DESCRIPTION} TEXT NOT NULL, " +
                "${TaskContract.TaskEntry.COLUMN_PRIORITY} INTEGER NOT NULL" +
                ");"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_NAME)
        onCreate(db)
    }

}