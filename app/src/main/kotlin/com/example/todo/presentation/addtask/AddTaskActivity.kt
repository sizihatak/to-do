package com.example.todo.presentation.addtask

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.example.todo.R
import com.example.todo.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_task)
        binding.viewModel = ViewModelProviders.of(this).get(AddTaskViewModel::class.java)
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
        }
    }
}