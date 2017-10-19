package com.example.todo.data.room

import android.arch.persistence.room.Room
import com.example.todo.BuildConfig
import com.example.todo.data.db.room.TaskEntity
import com.example.todo.data.db.room.TasksDataBase
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.concurrent.TimeUnit


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(25))
class TasksDaoTest {

    //TODO implement tests without delays

    private val testTask = TaskEntity(null, "title", "description", 3)
    private lateinit var mDatabase: TasksDataBase

    @Before
    fun initDb() {
        mDatabase = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application,
                TasksDataBase::class.java).allowMainThreadQueries().build()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun closeDb() {
        mDatabase.close()
    }

    @Test
    fun testInsertTaskAndGetTask() {
        mDatabase.taskDao().insertTask(testTask)

        val testSubscriber = mDatabase.
                taskDao().getTasks()
                .test()

        testSubscriber.await(1000, TimeUnit.MILLISECONDS)

        testSubscriber.assertValue {
            it[0].title == testTask.title
        }
        testSubscriber.assertValueCount(1)
    }

    @Test
    fun testDeleteTaskAndGetById() {
        mDatabase.taskDao().insertTask(testTask)

        val testSubscriber =
                mDatabase.taskDao().getTasks()
                        .delay(1000, TimeUnit.MILLISECONDS)
                        .map { list ->
                            mDatabase.taskDao().deleteTask(list[0].id.toString())
                        }
                        .delay(1000, TimeUnit.MILLISECONDS)
                        .map { mDatabase.taskDao().getTasks() }
                        .test()

        testSubscriber.await(1000, TimeUnit.MILLISECONDS)

        testSubscriber.assertNoValues()
    }
}