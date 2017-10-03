package com.example.todo.presentation.feature.tasklist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
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
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?,
                                target: RecyclerView.ViewHolder?): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                viewHolder?.let {
                    val position = viewHolder.layoutPosition
                    binding.viewModel.deleteTask(position, adapter.tasks[position])
                }
            }

        }).attachToRecyclerView(binding.rvMainTasklist)

        binding.viewModel.onDeleteTaskObserver.subscribe { position ->
            adapter.tasks.removeAt(position)
            adapter.notifyItemRemoved(position)
        }

        binding.viewModel.listTaskObservable.observe(this, Observer {
            it?.let { adapter.tasks = it.toMutableList() }
        })
    }

    override fun onResume() {
        super.onResume()
        binding.viewModel.getTaskList()
    }
}