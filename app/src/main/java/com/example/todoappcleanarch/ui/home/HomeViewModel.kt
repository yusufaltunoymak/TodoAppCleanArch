package com.example.todoappcleanarch.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoappcleanarch.data.ToDoRepository
import com.example.todoappcleanarch.model.Priority
import com.example.todoappcleanarch.model.ToDoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(application : Application, private val repository: ToDoRepository) : AndroidViewModel(application) {
    val toDoList = repository.localDataSource.getAllToDo().asLiveData()
    var searchToDoList : LiveData<List<ToDoModel>> = MutableLiveData()
    val searchQuery = MutableLiveData("")
    fun insertToDo() {
        viewModelScope.launch {
            repository.localDataSource.insertToDo(ToDoModel(title = "title", description = "desc", priority = Priority.HIGH, isChecked = true))
        }
    }

    fun updateToDo(toDoModel: ToDoModel) {
        val updatedToDoModel = toDoModel.copy(isChecked = toDoModel.isChecked?.not())
        viewModelScope.launch {
            repository.localDataSource.updateToDo(updatedToDoModel)
        }
    }

    fun searchToDo(searchQuery : String) {
        searchToDoList = repository.localDataSource.searchToDo("%$searchQuery%").asLiveData()
        this.searchQuery.value = searchQuery
    }
}