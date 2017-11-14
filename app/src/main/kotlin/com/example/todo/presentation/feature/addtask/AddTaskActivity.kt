package com.example.todo.presentation.feature.addtask

import android.arch.lifecycle.Observer
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.example.todo.R
import com.example.todo.ToDoApplication
import com.example.todo.databinding.ActivityAddTaskBinding
import com.example.todo.di.DaggerActivityComponent
import com.example.todo.di.module.ActivityModule
import javax.inject.Inject

class AddTaskActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddTaskBinding

    @Inject
    lateinit var viewModel: AddTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .appComponent((application as ToDoApplication).appComponent)
                .build()
                .inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_task)
        binding.viewModel = viewModel
        subscribeToModel(binding.viewModel as AddTaskViewModel)
    }

    fun subscribeToModel(viewModel: AddTaskViewModel) {
        viewModel.apply {
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
                        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                    })
            closeActivityObservable
                    .subscribe { finish() }
        }
    }
}