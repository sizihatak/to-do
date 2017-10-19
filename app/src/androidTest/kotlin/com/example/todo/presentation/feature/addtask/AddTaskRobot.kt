package com.example.todo.presentation.feature.addtask

import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.v4.content.ContextCompat
import com.example.todo.R
import com.example.todo.ToDoApplication
import com.example.todo.data.DataManagerWithLocalRoom
import com.example.todo.test.ScreenRobot

class AddTaskRobot : ScreenRobot() {

    enum class Priority {
        HIGH, MEDIUM, LOW
    }

    private val dataBaseManager: DataManagerWithLocalRoom = DataManagerWithLocalRoom(InstrumentationRegistry.getTargetContext())
    private val app = InstrumentationRegistry.getTargetContext().applicationContext as ToDoApplication

    fun launch(rule: ActivityTestRule<AddTaskActivity>): AddTaskRobot {
        rule.launchActivity(null)
        return this
    }

    fun titleHint(@StringRes stringId: Int): AddTaskRobot =
            checkHint(R.id.textInput_addTask_title, app.getString(stringId))


    fun descriptionHint(@StringRes stringId: Int): AddTaskRobot =
            checkHint(R.id.textInput_addTask_description, app.getString(stringId))

    fun clickOnCheckBox(checkBoxPriority: Priority): AddTaskRobot =
            when (checkBoxPriority) {
                Priority.HIGH -> clickOn(R.id.radioButton_addTask_priorityHigh)
                Priority.MEDIUM -> clickOn(R.id.radioButton_addTask_priorityMedium)
                Priority.LOW -> clickOn(R.id.radioButton_addTask_priorityLow)
            }

    fun checkCheckBoxIsChecked(checkBoxPriority: Priority): AddTaskRobot =
            when (checkBoxPriority) {
                Priority.HIGH -> checkIsChecked(R.id.radioButton_addTask_priorityHigh)
                Priority.MEDIUM -> checkIsChecked(R.id.radioButton_addTask_priorityMedium)
                Priority.LOW -> checkIsChecked(R.id.radioButton_addTask_priorityLow)
            }

    fun clickOnSubmit(): AddTaskRobot =
            clickOn(R.id.button_addTask_submit)

    fun checkButtonColor(@ColorRes colorId: Int): AddTaskRobot {
        val color = ContextCompat.getColor(app, colorId)
        return checkBackgroundColor(R.id.button_addTask_submit, color)
    }

    fun checkTitleError(@StringRes stringId: Int): AddTaskRobot =
            checkError(R.id.textInput_addTask_title, app.getString(stringId))

    fun fillTitle(text: String): AddTaskRobot =
            enterText(R.id.editText_addTask_title, text)

    fun fillDescroption(text: String): AddTaskRobot =
            enterText(R.id.editText_addTask_description, text)

    fun deleteTasks(): AddTaskRobot {
        dataBaseManager.dataBase.taskDao().clearTasks()
        return this
    }
}