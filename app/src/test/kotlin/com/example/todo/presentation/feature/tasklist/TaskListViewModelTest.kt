package com.example.todo.presentation.feature.tasklist

import android.app.Application
import android.arch.lifecycle.Lifecycle

import android.content.Context
import android.view.View
import com.example.todo.data.DataManager
import io.reactivex.Observer
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class TaskListViewModelTest {

    @Mock
    lateinit var dataManager: DataManager

    @Mock
    lateinit var lifecycle: Lifecycle

    @Mock
    lateinit var context: Application

    @Mock
    lateinit var application: Application

    lateinit var taskListViewModel: TaskListViewModel

    @Before
    fun setupViewModel() {
        MockitoAnnotations.initMocks(this)
        setupContext()
        taskListViewModel = TaskListViewModel(application, lifecycle, dataManager)
    }

    private fun setupContext() {
        `when`<Context>(context.applicationContext).thenReturn(context)
    }

    @Test
    fun getOnStartAddTaskScreenObserver() {
        assertNotNull(taskListViewModel.onStartAddTaskScreenObserver)
    }

    @Test
    fun getOnDeleteTaskObserver() {
        assertNotNull(taskListViewModel.onDeleteTaskObserver)
    }

    @Test
    fun getListTaskObservable() {
        assertNotNull(taskListViewModel.listTaskObservable)
    }

    @Test
    fun showAddTaskScreen() {
        val view = mock(View::class.java)
        val observer:Observer<in Unit> = mock()

        taskListViewModel.onStartAddTaskScreenObserver.subscribe(observer)
        taskListViewModel.showAddTaskScreen(view)
        verify(observer).onNext(Unit)
    }

    inline fun <reified T: Any> mock() = Mockito.mock(T::class.java)

    @Test
    fun deleteTask() {

    }

    @Test
    fun getTaskList() {

    }

    @Test
    fun onResume() {

    }

    @Test
    fun onDestroy() {

    }

}