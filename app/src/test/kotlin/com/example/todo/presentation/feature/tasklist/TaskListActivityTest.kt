package com.example.todo.presentation.feature.tasklist

import com.example.todo.BuildConfig
import com.example.todo.presentation.feature.ToDoApplicationTest
import com.nhaarman.mockito_kotlin.spy
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(25), application = ToDoApplicationTest::class)
class TaskListActivityTest {
    @Before
    fun setUp() {
        val controllerActivity = spy(Robolectric.buildActivity(TaskListActivity::class.java)).create()
    }

    @Test
    fun getViewModel() {
        assertNotNull("asd")
    }

    @Test
    fun setViewModel() {

    }

    @Test
    fun onCreate() {

    }

}