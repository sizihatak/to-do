package com.example.todo.data.db.provider

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.todo.BuildConfig
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.anyOrNull
import mock
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.reflect.Whitebox
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowContentResolver

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(25))
class TaskContentProviderTest {

    lateinit var shadowContentResolver: ShadowContentResolver
    lateinit var taskContentProvider: TaskContentProvider

    @Before
    fun setup() {
        taskContentProvider = TaskContentProvider()
        taskContentProvider.taskDbHelper = mock()
        Whitebox.setInternalState(taskContentProvider, "mContext", RuntimeEnvironment.application)
        shadowContentResolver = Shadows.shadowOf(RuntimeEnvironment.application.contentResolver)
    }

    @Test
    fun testCreate() {
        val taskContentProvider = TaskContentProvider()
        val context: Context = mock()
        Whitebox.setInternalState(taskContentProvider, "mContext", context)
        taskContentProvider.onCreate()
        assertNotNull(taskContentProvider.taskDbHelper)
    }

    @Test
    fun testInsert() {
        val testId = 42L

        val writableDatabase: SQLiteDatabase = mock()
        Mockito.`when`(taskContentProvider.taskDbHelper.writableDatabase).thenReturn(writableDatabase)
        Mockito.`when`(writableDatabase.insert(any(), anyOrNull(), any())).thenReturn(testId)

        val taskUri = TaskContract.TaskEntry.CONTENT_URI

        val resultUri = taskContentProvider.insert(taskUri, ContentValues())

        val notifiedUris = shadowContentResolver.notifiedUris
        assertEquals(notifiedUris[0].uri, taskUri)
        assertEquals(resultUri?.lastPathSegment, testId.toString())
    }

    @Test
    fun testDelete() {
        val testId = 43

        val writableDatabase: SQLiteDatabase = mock()
        Mockito.`when`(taskContentProvider.taskDbHelper.writableDatabase).thenReturn(writableDatabase)
        Mockito.`when`(writableDatabase.delete(any(), any(), any())).thenReturn(testId)

        val taskUri = TaskContract.TaskEntry.CONTENT_URI.buildUpon().appendPath(testId.toString()).build()

        val deletedId = taskContentProvider.delete(taskUri, null, null)

        val notifiedUris = shadowContentResolver.notifiedUris
        assertEquals(notifiedUris[0].uri, taskUri)
        assertEquals(deletedId, testId)
    }

    @Test
    fun testQuery() {
        val readableDatabaseMock: SQLiteDatabase = mock()
        val cursorMock: Cursor = mock()
        Mockito.`when`(taskContentProvider.taskDbHelper.readableDatabase).thenReturn(readableDatabaseMock)
        Mockito.`when`(readableDatabaseMock.query(any(),
                anyOrNull(),
                anyOrNull(),
                anyOrNull(),
                anyOrNull(),
                anyOrNull(),
                any())).thenReturn(cursorMock)
        val cursor = taskContentProvider.query(TaskContract.TaskEntry.CONTENT_URI,
                null,
                null,
                null,
                TaskContract.TaskEntry.COLUMN_PRIORITY)
        assertEquals(cursor, cursorMock)
        Mockito.verify(cursorMock)
                .setNotificationUri(RuntimeEnvironment.application.contentResolver, TaskContract.TaskEntry.CONTENT_URI)
    }
}