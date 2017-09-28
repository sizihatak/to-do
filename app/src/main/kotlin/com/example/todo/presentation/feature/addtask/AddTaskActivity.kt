package com.example.todo.presentation.feature.addtask

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.todo.R
import com.example.todo.databinding.ActivityAddTaskBinding

class AddTaskActivity : LifecycleActivity() {

    lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        val factory = AddTaskViewModelFactory(application)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_task)
        binding.viewModel = ViewModelProviders.of(this, factory).get(AddTaskViewModel::class.java)
        observe()
    }

    fun observe() {
        binding.viewModel.apply {
            titleErrorObservable
                    .observe(this@AddTaskActivity, Observer { error ->
                        error?.let {
                            if (error.isEmpty()) {
                                binding.textInputAddTaskTitle.isErrorEnabled = false
                            } else {
                                binding.textInputAddTaskTitle.error = error
                                binding.textInputAddTaskTitle.requestFocus()
                            }
                        }
                    })
            changeSubmitBackgroundColorObservable
                    .observe(this@AddTaskActivity, Observer { color ->
                        color?.let {
                            binding.buttonAddTaskSubmit.setBackgroundColor(color)
                        }
                    })
            hideKeyboardObservable
                    .observe(this@AddTaskActivity, Observer {
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                    })
            showToast
                    .subscribe { Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show() }
        }
    }
}