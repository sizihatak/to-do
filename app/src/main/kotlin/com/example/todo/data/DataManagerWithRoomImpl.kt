package com.example.todo.data

import android.content.Context
import com.example.todo.data.db.room.TaskDao
import com.example.todo.data.db.room.TasksDataBase
import com.example.todo.presentation.model.Task
import com.example.todo.toTask
import com.example.todo.toTaskEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.internal.operators.completable.CompletableFromAction

open class DataManagerWithRoomImpl (context: Context) : DataManager {

    open protected val taskDao: TaskDao =
            TasksDataBase.getInstance(context).taskDao()

    override fun saveTask(taskView: Task): Completable = CompletableFromAction {
        taskDao.insertTask(taskView.toTaskEntity())
    }

    override fun getTasks(): Flowable<List<Task>> =
            taskDao.getTasks().map { it.map { it.toTask() } }

    override fun deleteTask(id: String): Completable =
            CompletableFromAction { taskDao.deleteTask(id) }
}