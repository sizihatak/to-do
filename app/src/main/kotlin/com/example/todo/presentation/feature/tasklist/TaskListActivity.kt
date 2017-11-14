package com.example.todo.presentation.feature.tasklist

import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.todo.R
import com.example.todo.ToDoApplication
import com.example.todo.databinding.ActivityTasklistBinding
import com.example.todo.di.DaggerActivityComponent
import com.example.todo.di.module.ActivityModule
import com.example.todo.presentation.feature.addtask.AddTaskActivity
import javax.inject.Inject

class TaskListActivity : AppCompatActivity() {
    lateinit var binding: ActivityTasklistBinding

    lateinit var adapter: TaskListAdapter

    @Inject
    lateinit var viewModel: TaskListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasklist)

        DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .appComponent((application as ToDoApplication).appComponent)
                .build()
                .inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tasklist)
        binding.viewModel = viewModel

        binding.rvMainTasklist.layoutManager = LinearLayoutManager(applicationContext)
        adapter = TaskListAdapter()
        binding.rvMainTasklist.adapter = adapter
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?,
                                target: RecyclerView.ViewHolder?): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                viewHolder?.let {
                    val position = viewHolder.layoutPosition
                    binding.viewModel?.deleteTask(position, adapter.tasks[position])
                }
            }

        }).attachToRecyclerView(binding.rvMainTasklist)

        subscribeToModel()
    }

    private fun subscribeToModel() {
        viewModel.apply {
            onStartAddTaskScreenObserver.subscribe {
                startActivity(Intent(this@TaskListActivity, AddTaskActivity::class.java))
            }
            onDeleteTaskObserver.subscribe { position ->
                adapter.tasks.removeAt(position)
            }
            listTaskObservable.observe(this@TaskListActivity, Observer {
                it?.let { adapter.tasks = it.toMutableList() }
            })
        }
    }
}