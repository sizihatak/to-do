package com.example.todo.presentation.feature.tasklist

import com.example.todo.BuildConfig
import mock
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(25))
class TaskListViewModelFactoryTest {
    @Test
    fun testCreateTaskListViewModel() {
        val taskListViewModelFactory = TaskListViewModelFactory(RuntimeEnvironment.application, mock(), mock())
        val taskListViewModel = taskListViewModelFactory.create(TaskListViewModel::class.java)
        assertTrue(taskListViewModel is TaskListViewModel)
    }
}