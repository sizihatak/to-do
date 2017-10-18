package com.example.todo.presentation.feature.addtask

import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.v4.content.ContextCompat
import com.example.todo.R
import com.example.todo.ToDoApplication
import com.example.todo.test.ScreenRobot

class AddTaskRobot : ScreenRobot() {

    enum class Priority {
        HIGH, MEDIUM, LOW
    }

    private val app = InstrumentationRegistry.getTargetContext().applicationContext as ToDoApplication

    fun launch(rule: ActivityTestRule<AddTaskActivity>): AddTaskRobot {
        rule.launchActivity(null)
        return this
    }

    fun titleHint(stringHint: String): AddTaskRobot =
            checkHint(R.id.editText_addTask_title, stringHint)

    fun descriptionHint(stringHint: String): AddTaskRobot =
            checkHint(R.id.editText_addTask_title, stringHint)

    fun clickOnCheckBox(checkBoxPriority: Priority): AddTaskRobot =
            when (checkBoxPriority) {
                Priority.HIGH -> clickOn(R.id.radioButton_addTask_priorityHigh)
                Priority.MEDIUM -> clickOn(R.id.radioButton_addTask_priorityMedium)
                Priority.LOW -> clickOn(R.id.radioButton_addTask_priorityLow)
            }

    fun clickOnSubmit() : AddTaskRobot =
            clickOn(R.id.button_addTask_submit)

    fun checkButtonColor(@ColorRes colorId: Int) : AddTaskRobot{
            val color = ContextCompat.getColor(app, colorId)
            return checkBackgroundColor(R.id.button_addTask_submit, color)
    }

    fun checkTitleError(@StringRes stringId: Int) : AddTaskRobot =
            checkError(R.id.textInput_addTask_title, app.getString(stringId))
}