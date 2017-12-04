package com.example.todo.presentation.feature.login

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableField
import android.util.Log
import android.view.KeyEvent
import android.widget.TextView

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val email = ObservableField<String>()
    val password = ObservableField<String>()

    fun onEditorAction(textView: TextView,
                       actionId: Int,
                       keyEvent: KeyEvent?): Boolean {
        Log.d("Test", "test")
        return true
    }
}