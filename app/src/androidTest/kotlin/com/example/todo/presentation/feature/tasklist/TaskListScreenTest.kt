package com.example.todo.presentation.feature.tasklist

import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import com.example.todo.presentation.feature.addtask.AddTaskActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TaskListScreenTest {

    @Rule
    @JvmField
    val activityRule: IntentsTestRule<TaskListActivity>
            = IntentsTestRule(TaskListActivity::class.java, true, false)

    @Test
    fun startScreenWithTasks() {
        TaskListRobot(activityRule)
                .createTask(4)
                .launch()
                .listVisible()
                .deleteTasks()
    }

    @Test
    fun clickOnFab() {
        TaskListRobot(activityRule)
                .launch()
                .clickOnAddTask()
                .checkStartScreen(AddTaskActivity::class.java)
    }

    @Test
    fun swipeTask() {
        TaskListRobot(activityRule)
                .createTask(4)
                .launch()
                .leftSwipeTask(1)
                .checkTasksCount(3)
                .deleteTasks()
    }
}