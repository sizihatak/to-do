package com.example.todo.data

import android.content.Context
import com.example.todo.data.db.room.TaskDao
import mock

class DataManagerRoomMock(context: Context) : DataManagerWithRoomImpl(context) {
    public override val taskDao: TaskDao = mock()
}