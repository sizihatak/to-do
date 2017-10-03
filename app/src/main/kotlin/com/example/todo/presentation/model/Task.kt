package com.example.todo.presentation.model

data class Task(val id: Int = -1, val title: String, val description: String = "", val priority: Int)