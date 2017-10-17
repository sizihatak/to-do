package com.example.todo.data

import com.example.todo.BuildConfig
import com.example.todo.data.db.room.TaskDao
import com.example.todo.data.db.room.TaskEntity
import com.example.todo.data.db.room.TasksDataBase
import com.example.todo.presentation.model.Task
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Flowable
import mock
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(25))
class DataManagerWithRoomImplTest {

    private lateinit var taskDao: TaskDao
    private lateinit var dataManager: DataManager
    private val testTaskEntity = TaskEntity(3L, "titleTaskEntity", "descriptionTaskEntity", 1)
    private val testTask = Task(4L, "titleTask", "descriptionTask", 2)

    @Before
    fun setup() {
        val tasksDataBase: TasksDataBase = mock()
        taskDao = mock()

        TasksDataBase.INSTANCE = tasksDataBase
        Mockito.`when`(tasksDataBase.taskDao()).thenReturn(taskDao)
        dataManager = DataManagerWithRoomImpl(RuntimeEnvironment.application)
    }

    @Test
    fun testSaveTask() {
        dataManager.saveTask(testTask).subscribe()

        argumentCaptor<TaskEntity>().apply {
            verify(taskDao).insertTask(capture())
            Assert.assertEquals(testTask.id, firstValue.id)
            Assert.assertEquals(testTask.title, firstValue.title)
            Assert.assertEquals(testTask.description, firstValue.description)
            Assert.assertEquals(testTask.priority, firstValue.priority)
        }
    }

    @Test
    fun testGetTasks() {
        val listTask = mutableListOf(testTaskEntity)
        Mockito.`when`(taskDao.getTasks()).thenReturn(Flowable.just(listTask))

        dataManager.getTasks().subscribe()
        verify(taskDao).getTasks()
    }

    @Test
    fun testDeleteTask() {
        val testId = "3"
        dataManager.deleteTask(testId).subscribe()
        verify(taskDao).deleteTask(testId)
    }
}