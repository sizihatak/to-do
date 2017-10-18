package com.example.todo.data.provider

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.todo.BuildConfig
import com.example.todo.data.db.provider.TaskContentProvider
import com.example.todo.data.db.provider.TaskContract
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.anyOrNull
import mock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
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

    companion object {
        const val CONTEXT_FIELD = "mContext"
    }

    private val taskUri = TaskContract.TaskEntry.CONTENT_URI

    private lateinit var shadowContentResolver: ShadowContentResolver
    private lateinit var taskContentProvider: TaskContentProvider

    @get:Rule
    var expectedException = ExpectedException.none()

    @Before
    fun setup() {
        taskContentProvider = TaskContentProvider()
        taskContentProvider.taskDbHelper = mock()
        Whitebox.setInternalState(taskContentProvider, CONTEXT_FIELD, RuntimeEnvironment.application)
        shadowContentResolver = Shadows.shadowOf(RuntimeEnvironment.application.contentResolver)
    }

    @Test
    fun testCreate() {
        val taskContentProvider = TaskContentProvider()
        val context: Context = mock()
        Whitebox.setInternalState(taskContentProvider, CONTEXT_FIELD, context)
        taskContentProvider.onCreate()
        assertNotNull(taskContentProvider.taskDbHelper)
    }

    @Test
    fun testInsert() {
        val testId = 42L

        val writableDatabase: SQLiteDatabase = mock()
        Mockito.`when`(taskContentProvider.taskDbHelper.writableDatabase).thenReturn(writableDatabase)
        Mockito.`when`(writableDatabase.insert(any(), anyOrNull(), any())).thenReturn(testId)

        val resultUri = taskContentProvider.insert(taskUri, ContentValues())

        val notifiedUris = shadowContentResolver.notifiedUris
        assertEquals(notifiedUris[0].uri, taskUri)
        assertEquals(resultUri?.lastPathSegment, testId.toString())
    }

    @Test
    fun testInsertWithSQLException() {
        expectedException.expect(SQLException::class.java)
        expectedException.expectMessage("Failed to insert row into $taskUri")

        val testId = -1L

        val writableDatabase: SQLiteDatabase = mock()
        Mockito.`when`(taskContentProvider.taskDbHelper.writableDatabase).thenReturn(writableDatabase)
        Mockito.`when`(writableDatabase.insert(any(), anyOrNull(), any())).thenReturn(testId)

        taskContentProvider.insert(taskUri, ContentValues())
    }

    @Test
    fun testInsertWithUnsupportedOperationException() {
        val testWrongUri = taskUri.buildUpon().appendPath("test").build()

        expectedException.expect(UnsupportedOperationException::class.java)
        expectedException.expectMessage("Unknown uri: $testWrongUri")

        val writableDatabase: SQLiteDatabase = mock()
        Mockito.`when`(taskContentProvider.taskDbHelper.writableDatabase).thenReturn(writableDatabase)

        taskContentProvider.insert(testWrongUri, ContentValues())
    }

    @Test
    fun testQueryWithUnsupportedOperationException() {
        val testWrongUri = taskUri.buildUpon().appendPath("test").build()

        expectedException.expect(UnsupportedOperationException::class.java)
        expectedException.expectMessage("Unknown uri: $testWrongUri")

        val readableDatabaseMock: SQLiteDatabase = mock()
        Mockito.`when`(taskContentProvider.taskDbHelper.writableDatabase).thenReturn(readableDatabaseMock)

        taskContentProvider.query(testWrongUri, null, null, null, TaskContract.TaskEntry.COLUMN_PRIORITY)
    }

    @Test
    fun testDeleteWithUnsupportedOperationException() {
        val testWrongUri = taskUri.buildUpon().appendPath("test").build()

        expectedException.expect(UnsupportedOperationException::class.java)
        expectedException.expectMessage("Unknown uri: $testWrongUri")

        val writableDatabase: SQLiteDatabase = mock()
        Mockito.`when`(taskContentProvider.taskDbHelper.writableDatabase).thenReturn(writableDatabase)

        taskContentProvider.delete(testWrongUri, null, null)
    }

    @Test
    fun testDelete() {
        val testId = 43

        val writableDatabase: SQLiteDatabase = mock()
        Mockito.`when`(taskContentProvider.taskDbHelper.writableDatabase).thenReturn(writableDatabase)
        Mockito.`when`(writableDatabase.delete(any(), any(), any())).thenReturn(testId)

        val uriWithId = taskUri.buildUpon().appendPath(testId.toString()).build()

        val deletedId = taskContentProvider.delete(uriWithId, null, null)

        val notifiedUris = shadowContentResolver.notifiedUris
        assertEquals(notifiedUris[0].uri, uriWithId)
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
        val cursor = taskContentProvider.query(taskUri,
                null,
                null,
                null,
                TaskContract.TaskEntry.COLUMN_PRIORITY)
        assertEquals(cursor, cursorMock)
        Mockito.verify(cursorMock)
                .setNotificationUri(RuntimeEnvironment.application.contentResolver, TaskContract.TaskEntry.CONTENT_URI)
    }
}