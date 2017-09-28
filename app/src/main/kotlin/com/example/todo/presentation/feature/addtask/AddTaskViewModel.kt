package com.example.todo.presentation.feature.addtask

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import android.net.Uri
import android.view.View
import android.widget.RadioGroup
import com.example.todo.R
import com.example.todo.data.DataManager
import com.example.todo.presentation.model.Task
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject


class AddTaskViewModel(application: Application, private val dataManager: DataManager) : AndroidViewModel(application) {

    val title = ObservableField<String>()
    val description = ObservableField<String>()
    val changeSubmitBackgroundColorObservable: MutableLiveData<Int> = MutableLiveData()
    val titleErrorObservable: MutableLiveData<String> = MutableLiveData()
    val hideKeyboardObservable: MutableLiveData<Boolean> = MutableLiveData()
    val showToast: PublishSubject<Uri> = PublishSubject.create()


    private val red: Int = application.resources.getColor(R.color.materialRed)
    private val orange: Int = application.resources.getColor(R.color.materialOrange)
    private val yellow: Int = application.resources.getColor(R.color.materialYellow)
    private val emptyField: String = application.getString(R.string.error_addTask_emptyField)
    private val disposable = CompositeDisposable()
    private var currentTaskPriority = 0

    fun submit(view: View) {
        hideKeyboardObservable.value = true
        if (title.get() == null || title.get().isEmpty()) {
            titleErrorObservable.value = emptyField
            return
        }
        disposable.add(dataManager.saveTask(Task(title.get(), description.get(), currentTaskPriority))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ uri ->
                    showToast.onNext(uri)
                    disposable.clear()
                }))
    }

    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        titleErrorObservable.value = ""

    }

    fun onCheckedChanged(radioGroup: RadioGroup, id: Int) {
        hideKeyboardObservable.value = true
        currentTaskPriority = radioGroup.indexOfChild(radioGroup.findViewById(id))
        when (currentTaskPriority) {
            0 -> changeSubmitBackgroundColorObservable.value = red
            1 -> changeSubmitBackgroundColorObservable.value = orange
            2 -> changeSubmitBackgroundColorObservable.value = yellow
        }
    }
}
