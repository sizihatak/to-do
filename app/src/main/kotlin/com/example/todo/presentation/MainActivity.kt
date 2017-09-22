package com.example.todo.presentation

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.todo.R
import com.example.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewModel.onFabObserver.subscribe {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.viewModel = null
        binding.executePendingBindings()
    }
}