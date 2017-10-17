package com.example.todo.data.db.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

abstract @Database(entities = arrayOf(TaskEntity::class), version = 1)
class TasksDataBase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {

        // TODO implement creation of TasksDataBase with Dagger
        @Volatile var INSTANCE: TasksDataBase? = null

        fun getInstance(context: Context): TasksDataBase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        TasksDataBase::class.java, "Sample.db")
                        .build()
    }
}