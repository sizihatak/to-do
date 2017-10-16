package com.example.todo.data

import android.database.Cursor
import com.example.todo.BuildConfig
import com.example.todo.data.db.provider.TaskContract
import com.example.todo.presentation.model.Task
import com.nhaarman.mockito_kotlin.any
import mock

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito

import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowContentResolver

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(25))
class DataManagerImplTest {

    lateinit var shadowContentResolver: ShadowContentResolver
    lateinit var dataManagerImpl: DataManagerImpl


    @Before
    fun setup() {
        shadowContentResolver = Shadows.shadowOf(RuntimeEnvironment.application.contentResolver)
        dataManagerImpl = DataManagerImpl(RuntimeEnvironment.application)
    }

    @Test
    fun deleteTask() {
        val testTaskId = "3"
        val expectedUriString = TaskContract.TaskEntry.CONTENT_URI.buildUpon().appendPath(testTaskId).build().toString()

        dataManagerImpl.deleteTask(testTaskId).subscribe()

        val resultUriString = shadowContentResolver.deleteStatements[0].uri.toString()
        val resultSelectionArgs = shadowContentResolver.deleteStatements[0].selectionArgs
        val resultWhere = shadowContentResolver.deleteStatements[0].where

        assertEquals(expectedUriString, resultUriString)
        assertNull(resultSelectionArgs)
        assertNull(resultWhere)
    }

    @Test
    fun saveTask() {
        val testId = 3L
        val testTitle = "title"
        val testDescription = "description"
        val testPriority = 1

        val testTask = Task(testId, testTitle, testDescription, testPriority)

        dataManagerImpl.saveTask(testTask).subscribe()
        val resultContentValues = shadowContentResolver.insertStatements[0].contentValues

        val resultTitle = resultContentValues.get(TaskContract.TaskEntry.COLUMN_TITLE)
        val resultDescription = resultContentValues.get(TaskContract.TaskEntry.COLUMN_DESCRIPTION)
        val resultPriority = resultContentValues.get(TaskContract.TaskEntry.COLUMN_PRIORITY)

        assertEquals(testTitle, resultTitle)
        assertEquals(testDescription, resultDescription)
        assertEquals(testPriority, resultPriority)
    }

    @Test
    fun getTasks() {
        dataManagerImpl.getTasks().subscribe()
        assertEquals(1, shadowContentResolver.getContentObservers(TaskContract.TaskEntry.CONTENT_URI).size)
    }

    @Test
    fun fromCursorToTasks() {
        val cursor: Cursor = mock()
        val dumbString = "test String"
        val dumbInt = 42

        Mockito.`when`(cursor.count).thenReturn(1)
        Mockito.`when`(cursor.getInt(any())).thenReturn(dumbInt)
        Mockito.`when`(cursor.getString(any())).thenReturn(dumbString)
        Mockito.`when`(cursor.getLong(any())).thenReturn(dumbInt.toLong())
        Mockito.`when`(cursor.moveToPosition(any())).thenReturn(true)

        val resultList = dataManagerImpl.fromCursorToTasks(cursor)

        assertEquals(1, resultList.size)
        assertEquals(dumbInt, resultList[0].priority)
        assertEquals(dumbString, resultList[0].title)
        assertEquals(dumbString, resultList[0].description)
        assertEquals(dumbInt.toLong(), resultList[0].id)
    }
}