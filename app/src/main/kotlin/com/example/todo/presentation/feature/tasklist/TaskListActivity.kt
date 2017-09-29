package com.example.todo.presentation.feature.tasklist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.todo.R
import com.example.todo.databinding.ActivityTasklistBinding
import com.example.todo.presentation.feature.addtask.AddTaskActivity

class TaskListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTasklistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasklist)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tasklist)
        binding.viewModel = ViewModelProviders.of(this, TaskListViewModelFactory(application)).get(TaskListViewModel::class.java)
        binding.viewModel.onStartAddTaskScreenObserver.subscribe {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }

        binding.rvMainTasklist.layoutManager = LinearLayoutManager(applicationContext)
        val adapter = TaskListAdapter()
        binding.rvMainTasklist.adapter = adapter

        binding.viewModel.listTaskObservable.observe(this, Observer {
            it?.let { adapter.tasks = it }
        })
    }

    override fun onResume() {
        super.onResume()
        binding.viewModel.getTaskList()
    }
}