package com.example.todo.presentation.feature.addtask

import com.example.todo.BuildConfig
import com.example.todo.presentation.feature.tasklist.TaskListActivity
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(25))
class AddTaskActivityTest {

    lateinit var activity: AddTaskActivity

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(AddTaskActivity::class.java)
    }

    @Test
    fun checkNotNullActivity() {
        assertNotNull(activity)
        activity.apply {
            assertNotNull(viewModel)
            assertNotNull(binding)
            assertNotNull(binding.textInputAddTaskTitle)
            assertNotNull(binding.buttonAddTaskSubmit)
        }
    }

    @Test
    fun checkTitleError() {
        val testString = "Test String"

        activity.apply {
            viewModel.titleErrorObservable.value = testString
            assertTrue(binding.textInputAddTaskTitle.isErrorEnabled)
            assertEquals(testString, binding.textInputAddTaskTitle.error.toString())

            viewModel.titleErrorObservable.value = ""
            assertFalse(binding.textInputAddTaskTitle.isErrorEnabled)
        }
    }
}