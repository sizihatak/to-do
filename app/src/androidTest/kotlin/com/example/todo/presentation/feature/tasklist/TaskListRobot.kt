package com.example.todo.presentation.feature.tasklist

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.rule.IntentsTestRule
import com.example.todo.R
import com.example.todo.data.DataManagerWithLocalRoom
import com.example.todo.presentation.model.Task
import com.example.todo.test.ScreenRobot
import com.example.todo.toTaskEntity

class TaskListRobot(private val rule: IntentsTestRule<TaskListActivity>) : ScreenRobot() {

    private val dataBaseManager: DataManagerWithLocalRoom = DataManagerWithLocalRoom(InstrumentationRegistry.getTargetContext().applicationContext)

    fun launch(): TaskListRobot {
        rule.launchActivity(null)
        return this
    }

    fun createTask(count: Int): TaskListRobot {
        for (i in 1..count)
            dataBaseManager.saveTask(
                    Task(title = "Title $i", description = "description $i", priority = i % 3))
                    .subscribe()
        return this
    }

    fun deleteTasks(): TaskListRobot {
        dataBaseManager.clearTasks().subscribe()
        return this
    }

    fun clickOnAddTask(): TaskListRobot =
            clickOn(R.id.fab_main_addbutton)

    fun listVisible(): TaskListRobot =
            checkIsVisible(R.id.rv_main_tasklist)

    fun checkStartScreen(cls: Class<*>): TaskListRobot {
        Intents.intended(IntentMatchers.hasComponent(cls.name))
        return this
    }

    fun leftSwipeTask(position: Int): TaskListRobot =
            swipeItemOfRecyclerView<TaskListRobot, TaskListAdapter.TaskViewHolder>(R.id.rv_main_tasklist, position, ViewActions.swipeLeft())


    fun checkTasksCount(count: Int): TaskListRobot =
            checkChildrenCount(R.id.rv_main_tasklist, count)

}