package com.example.todo.presentation.feature.addtask

import android.widget.Button
import com.example.todo.BuildConfig
import com.nhaarman.mockito_kotlin.verify
import mock
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.reflect.Whitebox
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(25))
class AddTaskActivityTest {

    lateinit var activity: AddTaskActivity

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(AddTaskActivity::class.java)
    }

    @Test
    fun testNotNullActivity() {
        assertNotNull(activity)
        activity.apply {
            assertNotNull(viewModel)
            assertNotNull(binding)
            assertNotNull(binding.textInputAddTaskTitle)
            assertNotNull(binding.buttonAddTaskSubmit)
        }
    }

    @Test
    fun testTitleError() {
        val testString = "Test String"

        activity.apply {
            viewModel.titleErrorObservable.value = testString
            assertTrue(binding.textInputAddTaskTitle.isErrorEnabled)
            assertEquals(testString, binding.textInputAddTaskTitle.error.toString())

            viewModel.titleErrorObservable.value = ""
            assertFalse(binding.textInputAddTaskTitle.isErrorEnabled)
        }
    }

    @Test
    fun testCloseActivity() {
        val shadowActivity = Shadows.shadowOf(activity)
        activity.viewModel.closeActivityObservable.onNext(Unit)
        assertTrue(shadowActivity.isFinishing)
    }

    @Test
    fun testСhangeSubmitBackgroundColor() {
        val testColor = 43
        val buttonMock : Button = mock()
        activity.apply {
            Whitebox.setInternalState(activity.binding, "buttonAddTaskSubmit", buttonMock)
            viewModel.changeSubmitBackgroundColorObservable.value = testColor

            verify(buttonMock).setBackgroundColor(testColor)
        }
    }
}