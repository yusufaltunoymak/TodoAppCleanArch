package com.example.todoappcleanarch.ui.newAndEdit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoappcleanarch.data.ToDoRepository
import com.example.todoappcleanarch.model.Priority
import com.example.todoappcleanarch.model.ToDoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewAndEditViewModel @Inject constructor(
    application: Application,
    private val repository: ToDoRepository
) : AndroidViewModel(application) {
    val toDoModel = MutableLiveData<ToDoModel>()

    fun insertToDo(title: String, description: String, isChecked: Boolean, priority: Priority) {
        viewModelScope.launch {
            repository.localDataSource.insertToDo(
                ToDoModel(
                    title = title,
                    description = description,
                    priority = priority,
                    isChecked = isChecked
                )
            )
        }
    }

    fun getToDo(toDoId : Int) {
        viewModelScope.launch {
            val toDo =repository.localDataSource.getToDo(toDoId)
            toDoModel.value = toDo
        }
    }

    fun updateToDo(title: String, description: String, isChecked: Boolean, priority: Priority) {
        viewModelScope.launch {
            repository.localDataSource.updateToDo(toDoModel = ToDoModel(
                id = toDoModel.value?.id ?: 0,
                title = title,
                description = description,
                isChecked = isChecked,
                priority = priority
            ))
        }
    }

    fun deleteToDo() {
        viewModelScope.launch {
            toDoModel.value?.let {
                repository.localDataSource.deleteToDo(it)
            }
        }
    }

}