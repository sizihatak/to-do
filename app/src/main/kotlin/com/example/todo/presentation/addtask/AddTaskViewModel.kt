package com.example.todo.presentation.addtask

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import android.view.View
import android.widget.RadioGroup
import com.example.todo.R


class AddTaskViewModel(application: Application) : AndroidViewModel(application) {

    private val red: Int = application.resources.getColor(R.color.materialRed)
    private val orange: Int = application.resources.getColor(R.color.materialOrange)
    private val yellow: Int = application.resources.getColor(R.color.materialYellow)
    private val emptyField: String = application.getString(R.string.error_addTask_emptyField)

    val title = ObservableField<String>()
    val description = ObservableField<String>()
    val changeSubmitBackgroundColorObservable: MutableLiveData<Int> = MutableLiveData()
    val titleErrorObservable: MutableLiveData<String> = MutableLiveData()
    val hideKeyboardObservable: MutableLiveData<Boolean> = MutableLiveData()

    fun submit(view: View) {
        hideKeyboardObservable.value = true
        if (title.get() == null || title.get().isEmpty()) {
            titleErrorObservable.value = emptyField
            return
        }
    }

    fun onTextChanged(s:CharSequence, start: Int, before: Int, count:Int){
        titleErrorObservable.value = ""
    }

    fun onCheckedChanged(radioGroup: RadioGroup, id: Int) {
        hideKeyboardObservable.value = true
        when (radioGroup.indexOfChild(radioGroup.findViewById(id))) {
            0 -> changeSubmitBackgroundColorObservable.value = red
            1 -> changeSubmitBackgroundColorObservable.value = orange
            2 -> changeSubmitBackgroundColorObservable.value = yellow
        }
    }
}