package com.example.todoappcleanarch.data

import com.example.todoappcleanarch.model.ToDoModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val toDoDao : TodoDao
) {
    fun getAllToDo() : Flow<List<ToDoModel>> = toDoDao.getAllToDo()

    fun searchToDo(searchQuery : String) : Flow<List<ToDoModel>> = toDoDao.searchToDo(searchQuery)

    suspend fun insertToDo(toDoModel: ToDoModel) {
        toDoDao.insertToDo(toDoModel)
    }

    suspend fun getToDo(toDoId: Int) : ToDoModel {
        return toDoDao.getToDo(toDoId)
    }

    suspend fun updateToDo(toDoModel: ToDoModel) {
        toDoDao.updateToDo(toDoModel)
    }

    suspend fun deleteToDo(toDoModel: ToDoModel) {
        toDoDao.deleteToDo(toDoModel)
    }
}