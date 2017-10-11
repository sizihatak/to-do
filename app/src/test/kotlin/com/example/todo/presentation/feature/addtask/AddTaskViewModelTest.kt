package com.example.todo.presentation.feature.addtask

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.RadioGroup
import com.example.todo.R
import com.example.todo.data.DataManager
import com.example.todo.presentation.model.Task
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.times
import io.reactivex.Completable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import mock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.`when`
import org.powermock.reflect.Whitebox



class AddTaskViewModelTest {

    companion object {
        const val CURRENT_TASK_PRIORITY_FIELD = "currentTaskPriority"
        const val ON_DELETE_TASK_OBSERVER_FIELD = "onDeleteTaskObserver"
        const val RED_COLOR = 43
        const val ORANGE_COLOR = 44
        const val YELLOW_COLOR = 45
        const val EMPTY_STRING = "Empty Field"
        const val DUMB_INT = 123
        const val DUMB_STRING = "test string"
    }

    @get:Rule var instantExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var dataManager: DataManager

    @Mock lateinit var lifecycle: Lifecycle

    @Mock lateinit var application: Application

    @Captor private lateinit var tasksCaptor: ArgumentCaptor<Task>

    lateinit var addTaskViewModel: AddTaskViewModel

    @Before
    fun setupViewModel() {
        MockitoAnnotations.initMocks(this)
        setupContext()
        addTaskViewModel = Mockito.spy(AddTaskViewModel(application, dataManager))
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    private fun setupContext() {
        `when`<Context>(application.applicationContext).thenReturn(application)
        `when`(application.getString(R.string.error_addTask_emptyField)).thenReturn(EMPTY_STRING)
        val resources: Resources = mock()
        `when`(application.resources).thenReturn(resources)
        `when`(resources.getColor(R.color.materialRed))
                .thenReturn(RED_COLOR)
        `when`(resources.getColor(R.color.materialOrange))
                .thenReturn(ORANGE_COLOR)
        `when`(resources.getColor(R.color.materialYellow))
                .thenReturn(YELLOW_COLOR)
    }

    @Test
    fun getTitle() {
        assertNotNull(addTaskViewModel.title)
    }

    @Test
    fun getDescription() {
        assertNotNull(addTaskViewModel.description)
    }

    @Test
    fun getChangeSubmitBackgroundColorObservable() {
        assertNotNull(addTaskViewModel.changeSubmitBackgroundColorObservable)
    }

    @Test
    fun getTitleErrorObservable() {
        assertNotNull(addTaskViewModel.titleErrorObservable)
    }

    @Test
    fun getHideKeyboardObservable() {
        assertNotNull(addTaskViewModel.hideKeyboardObservable)
    }

    @Test
    fun getCloseActivityObservable() {
        assertNotNull(addTaskViewModel.closeActivityObservable)
    }

    @Test
    fun submitWithNullAndEmptyTitle() {
        val observerForHideKeyboard: Observer<Boolean> = mock()
        val observerTitleError: Observer<String> = mock()

        addTaskViewModel.hideKeyboardObservable.observeForever(observerForHideKeyboard)
        addTaskViewModel.titleErrorObservable.observeForever(observerTitleError)

        addTaskViewModel.title.set(null)
        addTaskViewModel.submit(mock())

        addTaskViewModel.title.set("")
        addTaskViewModel.submit(mock())

        Mockito.verify(observerForHideKeyboard, times(2)).onChanged(true)
        Mockito.verify(observerTitleError, times(2)).onChanged(EMPTY_STRING)
        Mockito.verify(dataManager, never()).saveTask(any())
    }

    @Test
    fun submit() {
        val observerForHideKeyboard: Observer<Boolean> = mock()
        val observerTitleError: Observer<String> = mock()
        val observerCloseActivity: io.reactivex.Observer<Unit> = mock()

        addTaskViewModel.hideKeyboardObservable.observeForever(observerForHideKeyboard)
        addTaskViewModel.titleErrorObservable.observeForever(observerTitleError)
        addTaskViewModel.closeActivityObservable.subscribe(observerCloseActivity)

        addTaskViewModel.title.set(DUMB_STRING)
        addTaskViewModel.description.set(DUMB_STRING)
        Whitebox.setInternalState(addTaskViewModel, CURRENT_TASK_PRIORITY_FIELD, DUMB_INT)

        `when`(dataManager.saveTask(any())).thenReturn(Completable.complete())

        addTaskViewModel.submit(mock())
        Mockito.verify(observerForHideKeyboard).onChanged(true)
        Mockito.verify(observerTitleError, never()).onChanged(EMPTY_STRING)

        argumentCaptor<Task>().apply {
            Mockito.verify(dataManager).saveTask(capture())

            assertEquals(DUMB_STRING, firstValue.title)
            assertEquals(DUMB_STRING, firstValue.description)
            assertEquals(DUMB_INT, firstValue.priority)
        }

        Mockito.verify(observerCloseActivity).onNext(Unit)
    }

    @Test
    fun onTextChanged() {
        val observer: Observer<String> = mock()

        addTaskViewModel.titleErrorObservable.observeForever(observer)
        addTaskViewModel.onTextChanged(DUMB_STRING, DUMB_INT, DUMB_INT, DUMB_INT)

        Mockito.verify(observer).onChanged("")
    }

    @Test
    fun onCheckedChanged() {
        val firstViewId = 1100
        val secondViewId = 1101
        val thirdViewId = 1110
        val firstRadioButtonMock: View = mock()
        val secondRadioButtonMock: View = mock()
        val thirdRadioButtonMock: View = mock()

        val radioGroupMock: RadioGroup = mock()

        `when`<View>(radioGroupMock.findViewById(firstViewId)).thenReturn(firstRadioButtonMock)
        `when`<View>(radioGroupMock.findViewById(secondViewId)).thenReturn(secondRadioButtonMock)
        `when`<View>(radioGroupMock.findViewById(thirdViewId)).thenReturn(thirdRadioButtonMock)

        `when`(radioGroupMock.indexOfChild(firstRadioButtonMock)).thenReturn(0)
        `when`(radioGroupMock.indexOfChild(secondRadioButtonMock)).thenReturn(1)
        `when`(radioGroupMock.indexOfChild(thirdRadioButtonMock)).thenReturn(2)

        val observer: Observer<Int> = mock()
        addTaskViewModel.changeSubmitBackgroundColorObservable.observeForever(observer)

        addTaskViewModel.onCheckedChanged(radioGroupMock, firstViewId)
        Mockito.verify(observer, times(1)).onChanged(RED_COLOR)

        addTaskViewModel.onCheckedChanged(radioGroupMock, secondViewId)
        Mockito.verify(observer, times(1)).onChanged(ORANGE_COLOR)

        addTaskViewModel.onCheckedChanged(radioGroupMock, thirdViewId)
        Mockito.verify(observer, times(1)).onChanged(YELLOW_COLOR)
    }
}