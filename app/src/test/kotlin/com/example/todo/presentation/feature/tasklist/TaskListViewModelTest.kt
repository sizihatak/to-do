package com.example.todo.presentation.feature.tasklist

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.view.View
import com.example.todo.data.DataManager
import com.example.todo.presentation.model.Task
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observer
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import mock
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.powermock.reflect.Whitebox


class TaskListViewModelTest {

    companion object {
        const val COMPOSITE_DISPOSABLE_FIELD = "disposable"
        const val ON_DELETE_TASK_OBSERVER_FIELD = "onDeleteTaskObserver"
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var dataManager: DataManager

    @Mock
    lateinit var lifecycle: Lifecycle

    @Mock
    lateinit var application: Application

    lateinit var taskListViewModel: TaskListViewModel

    @Before
    fun setupViewModel() {
        MockitoAnnotations.initMocks(this)
        taskListViewModel = spy(TaskListViewModel(application, lifecycle, dataManager))
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
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
        val observer: Observer<in Unit> = mock()

        taskListViewModel.onStartAddTaskScreenObserver.subscribe(observer)
        taskListViewModel.showAddTaskScreen(view)
        verify(observer).onNext(Unit)
    }

    @Test
    fun deleteTask() {
        val positionTest = 10
        val idTest = 42L
        val testTask = Task(idTest)

        val onDeleteTaskObserver: PublishSubject<Int> = mock()
        doNothing().`when`(onDeleteTaskObserver).onNext(any(Int::class.java))
        Whitebox.setInternalState(taskListViewModel, ON_DELETE_TASK_OBSERVER_FIELD, onDeleteTaskObserver)

        `when`(dataManager.deleteTask(idTest.toString())).thenReturn(Completable.complete())

        taskListViewModel.deleteTask(positionTest, testTask)
        verify(onDeleteTaskObserver).onNext(positionTest)
    }

    @Test
    fun getTaskList() {
        val listMock: List<Task> = mock()
        `when`(dataManager.getTasks()).thenReturn(Flowable.just(listMock))

        val observer: android.arch.lifecycle.Observer<List<Task>> = mock()

        taskListViewModel.listTaskObservable.observeForever(observer)
        taskListViewModel.getTaskList()

        verify(observer).onChanged(listMock)
    }

    @Test
    fun onResume() {
        doNothing().`when`(taskListViewModel).getTaskList()
        taskListViewModel.onResume()
        verify(taskListViewModel).getTaskList()
    }

    @Test
    fun onDestroy() {
        val compositeDisposable: CompositeDisposable = mock()
        Whitebox.setInternalState(taskListViewModel, COMPOSITE_DISPOSABLE_FIELD, compositeDisposable)
        taskListViewModel.onDestroy()
        verify(compositeDisposable).clear()
    }
}
