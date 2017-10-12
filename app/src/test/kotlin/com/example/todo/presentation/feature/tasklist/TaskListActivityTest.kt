package com.example.todo.presentation.feature.tasklist

import com.example.todo.BuildConfig
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class TaskListActivityTest {

    lateinit var activity:TaskListActivity

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
        assertNotNull(activity.binding.rvMainTasklist.adapter)
    }

}