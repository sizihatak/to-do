package com.example.todo.presentation.feature.addtask

import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.todo.R
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AddTaskScreenTest {

    @Rule
    @JvmField
    val activityRule: ActivityTestRule<AddTaskActivity>
            = ActivityTestRule(AddTaskActivity::class.java, true, false)

    @Test
    fun startScreen() {
        AddTaskRobot(activityRule)
                .launch()
                .titleHint(R.string.addTask_title)
                .descriptionHint(R.string.addTask_description)
                .checkButtonColor(R.color.materialRed)
                .checkCheckBoxIsChecked(AddTaskRobot.Priority.HIGH)
    }

    @Test
    fun submitWithoutTitle() {
        AddTaskRobot(activityRule)
                .launch()
                .clickOnSubmit()
                .checkTitleError(R.string.error_addTask_emptyField)
    }

    @Test
    fun clicksOnCheckBox() {
        AddTaskRobot(activityRule)
                .launch()
                .clickOnCheckBox(AddTaskRobot.Priority.MEDIUM)
                .checkButtonColor(R.color.materialOrange)
                .clickOnCheckBox(AddTaskRobot.Priority.LOW)
                .checkButtonColor(R.color.materialYellow)
                .clickOnCheckBox(AddTaskRobot.Priority.HIGH)
                .checkButtonColor(R.color.materialRed)
    }

    @Test
    fun addTask() {
        AddTaskRobot(activityRule)
                .launch()
                .fillTitle("Test title integration")
                .fillDescroption("Test description integration")
                .clickOnCheckBox(AddTaskRobot.Priority.MEDIUM)
                .clickOnSubmit()
                .deleteTasks()
        assertTrue(activityRule.activity.isFinishing)
    }
}