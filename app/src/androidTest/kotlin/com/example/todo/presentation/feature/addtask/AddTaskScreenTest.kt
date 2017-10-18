package com.example.todo.presentation.feature.addtask

import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.todo.R
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
        AddTaskRobot()
                .launch(activityRule)
                .titleHint(R.string.addTask_title)
                .descriptionHint(R.string.addTask_description)
                .checkButtonColor(R.color.materialRed)
                .checkCheckBoxIsChecked(AddTaskRobot.Priority.HIGH)
    }

    @Test
    fun submitWithoutTitle() {
        AddTaskRobot()
                .launch(activityRule)
                .clickOnSubmit()
                .checkTitleError(R.string.error_addTask_emptyField)
    }

    @Test
    fun clicksOnCheckBox() {
        AddTaskRobot()
                .launch(activityRule)
                .clickOnCheckBox(AddTaskRobot.Priority.MEDIUM)
                .checkButtonColor(R.color.materialOrange)
                .clickOnCheckBox(AddTaskRobot.Priority.LOW)
                .checkButtonColor(R.color.materialYellow)
                .clickOnCheckBox(AddTaskRobot.Priority.HIGH)
                .checkButtonColor(R.color.materialRed)
    }
}