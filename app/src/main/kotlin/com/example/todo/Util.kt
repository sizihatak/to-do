package com.example.todo

import com.example.todo.data.db.room.TaskEntity
import com.example.todo.presentation.model.Task

fun Task.toTaskEntity(): TaskEntity =
        TaskEntity(id = if (this.id == -1L) null else this.id,
                title = this.title,
                description = this.description,
                priority = this.priority)

fun TaskEntity.toTask(): Task =
        Task(id = this.id ?: -1,
                title = this.title,
                description = this.description,
                priority = this.priority)