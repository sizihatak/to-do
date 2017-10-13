package com.example.todo.presentation.feature.tasklist

import com.example.todo.BuildConfig
import com.example.todo.presentation.feature.addtask.AddTaskActivity
import com.example.todo.presentation.model.Task
import com.nhaarman.mockito_kotlin.verify
import mock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(25))
class TaskListActivityTest {

    lateinit var activity: TaskListActivity

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(TaskListActivity::class.java)
    }

    @Test
    fun testNotNullActivity() {
        assertNotNull(activity)
        activity.apply {
            assertNotNull(viewModel)
            assertNotNull(binding)
            assertNotNull(binding.rvMainTasklist)
            assertNotNull(binding.rvMainTasklist.adapter)
        }
    }

    @Test
    fun testStartAddTaskScreen() {
        activity.viewModel.onStartAddTaskScreenObserver.onNext(Unit)
        val startedIntent = shadowOf(activity).nextStartedActivity
        val shadowIntent = shadowOf(startedIntent)
        assertEquals(AddTaskActivity::class.java, shadowIntent.intentClass)
    }

    @Test
    fun testDeleteTaskFromList() {
        val testPosition = 3

        val adapterMock: TaskListAdapter = mock()
        val taskListMock: MutableList<Task> = mock()

        Mockito.`when`(adapterMock.tasks).thenReturn(taskListMock)
        activity.adapter = adapterMock

        activity.viewModel.onDeleteTaskObserver.onNext(testPosition)
        verify(taskListMock).removeAt(testPosition)
    }

    @Test
    fun testSetTaskList() {
        activity.apply {
            val adapterMock = TaskListAdapter()
            adapter = adapterMock

            val testTask: Task = mock()
            val taskListMock: List<Task> = listOf(testTask)

            viewModel.listTaskObservable.value = taskListMock
            assertEquals(testTask, adapter.tasks[0])
        }
    }
}