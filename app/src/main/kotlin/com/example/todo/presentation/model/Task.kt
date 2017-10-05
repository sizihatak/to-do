package com.example.todo.presentation.model

data class Task(val id: Long = -1, val title: String, val description: String = "", val priority: Int)