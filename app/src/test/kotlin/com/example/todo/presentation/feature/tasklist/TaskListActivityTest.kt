package com.example.todo.presentation.feature.tasklist

import android.content.Context
import com.example.todo.BuildConfig
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowIntent
import android.content.Intent
import com.example.todo.presentation.feature.addtask.AddTaskActivity
import com.example.todo.presentation.model.Task
import com.nhaarman.mockito_kotlin.verify
import mock
import org.apache.tools.ant.TaskAdapter
import org.junit.Assert.*
import org.mockito.Mockito
import org.robolectric.Shadows.shadowOf


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class TaskListActivityTest {

    lateinit var activity: TaskListActivity

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(TaskListActivity::class.java)
    }

    @Test
    fun checkNotNullActivity() {
        assertNotNull(activity)
        assertNotNull(activity.binding)
        assertNotNull(activity.viewModel)
        assertNotNull(activity.binding.rvMainTasklist)
        assertNotNull(activity.binding.rvMainTasklist.adapter)
    }

    @Test
    fun checkStartAddTaskScreen() {
        activity.viewModel.onStartAddTaskScreenObserver.onNext(Unit)
        val startedIntent = shadowOf(activity).nextStartedActivity
        val shadowIntent = shadowOf(startedIntent)
        assertEquals(AddTaskActivity::class.java, shadowIntent.intentClass)
    }

    @Test
    fun checkDeleteTaskFromList() {
        val testPosition = 3

        val adapterMock: TaskListAdapter = mock()
        val taskListMock: MutableList<Task> = mock()

        Mockito.`when`(adapterMock.tasks).thenReturn(taskListMock)
        activity.adapter = adapterMock

        activity.viewModel.onDeleteTaskObserver.onNext(testPosition)
        verify(taskListMock).removeAt(testPosition)
    }

    @Test
    fun checkSetTaskList() {
        val adapterMock = TaskListAdapter()
        activity.adapter = adapterMock

        val testTask: Task = mock()

        val taskListMock: List<Task> = listOf(testTask)

        activity.viewModel.listTaskObservable.value = taskListMock

        assertEquals(testTask, activity.adapter.tasks[0])
    }
}