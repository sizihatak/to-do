package com.example.todo.data.db.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
public interface TaskDao {
    @Insert
    fun insertTask(task: TaskTable)

    @Query("DELETE FROM tasks WHERE id=:id")
    fun deleteTask(id: String)

    @Query("SELECT * FROM Users")
    fun getUser(): Flowable<List<TaskTable>>
}