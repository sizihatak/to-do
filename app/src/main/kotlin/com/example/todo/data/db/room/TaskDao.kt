package com.example.todo.data.db.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface TaskDao {
    @Insert
    fun insertTask(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE id=:id")
    fun deleteTask(id: String)

    @Query("SELECT * FROM tasks ORDER BY priority ASC")
    fun getTasks(): Flowable<List<TaskEntity>>

    @Query("DELETE FROM tasks")
    fun clearTasks()
}