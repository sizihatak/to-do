package com.example.todo.presentation.tasklist

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.todo.R
import com.example.todo.databinding.ActivityTasklistBinding
import com.example.todo.presentation.addtask.AddTaskActivity

class TaskListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTasklistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasklist)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tasklist)
        binding.viewModel = ViewModelProviders.of(this).get(TaskListViewModel::class.java)
        binding.viewModel.onStartAddTaskScreenObserver.subscribe {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }

}