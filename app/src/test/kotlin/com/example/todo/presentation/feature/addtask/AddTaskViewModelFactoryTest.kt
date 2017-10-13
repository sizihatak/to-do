package com.example.todo.presentation.feature.addtask

import com.example.todo.BuildConfig
import mock
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(25))class AddTaskViewModelFactoryTest {
    @Test
    fun testCreateAddTaskViewModel() {
        val addTaskViewModelFactory = AddTaskViewModelFactory(RuntimeEnvironment.application, mock())
        val addTaskViewModel = addTaskViewModelFactory.create(AddTaskViewModel::class.java)
        Assert.assertTrue(addTaskViewModel is AddTaskViewModel)
    }
}