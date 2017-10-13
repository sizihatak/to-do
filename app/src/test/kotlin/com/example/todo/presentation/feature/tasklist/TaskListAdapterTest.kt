package com.example.todo.presentation.feature.tasklist

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.todo.BuildConfig
import com.example.todo.R
import com.example.todo.databinding.ItemTaskBinding
import com.example.todo.presentation.model.Task
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(25))
class TaskListAdapterTest {

    lateinit var adapter: TaskListAdapter
    @Before
    fun setup() {
        adapter = spy()
    }

    @Test
    fun testSetTasks() {
        val testTasks: MutableList<Task> = mock()
        adapter.tasks = testTasks
        assertEquals(testTasks, adapter.tasks)
        verify(adapter).notifyDataSetChanged()
    }

    @Test
    fun testTasksCount() {
        val testTasks: MutableList<Task> = mock()
        val testCount = 5
        Mockito.`when`(testTasks.size).thenReturn(testCount)
        adapter.tasks = testTasks
        assertEquals(testCount, adapter.itemCount)
    }

    @Test
    fun testBindingFilter() {
        val constraintLayoutMock: ConstraintLayout = mock()
        val context: Context = RuntimeEnvironment.application

        val testRed: Int = context.resources.getColor(R.color.materialRed)
        val testOrange: Int = context.resources.getColor(R.color.materialOrange)
        val testYellow: Int = context.resources.getColor(R.color.materialYellow)

        Mockito.`when`(constraintLayoutMock.context).thenReturn(context)

        TaskListAdapter.applyFilter(constraintLayoutMock, 0)
        verify(constraintLayoutMock).setBackgroundColor(testRed)

        TaskListAdapter.applyFilter(constraintLayoutMock, 1)
        verify(constraintLayoutMock).setBackgroundColor(testOrange)

        TaskListAdapter.applyFilter(constraintLayoutMock, 2)
        verify(constraintLayoutMock).setBackgroundColor(testYellow)
    }

    @Test
    fun testCreatingViewHolder() {
        val recyclerView = RecyclerView(RuntimeEnvironment.application)
        val layoutManager = LinearLayoutManager(RuntimeEnvironment.application)
        recyclerView.layoutManager = layoutManager

        val viewHolder = adapter.createViewHolder(recyclerView, 0)

        assertNotNull(viewHolder)
        assertTrue(viewHolder is TaskListAdapter.TaskViewHolder)
    }

    @Test
    fun testBindViewHolder() {
        val positionTest = 3

        val taskViewHolderMock: TaskListAdapter.TaskViewHolder= mock()
        val tasksMock: MutableList<Task> = mock()
        val taskMock: Task = mock()
        val bindingItemMock: ItemTaskBinding= mock()
        Mockito.`when`(taskViewHolderMock.bindingItem).thenReturn(bindingItemMock)
        Mockito.`when`(tasksMock[positionTest]).thenReturn(taskMock)

        adapter.tasks = tasksMock
        adapter.onBindViewHolder(taskViewHolderMock, positionTest)
        verify(bindingItemMock).task = taskMock
    }
}