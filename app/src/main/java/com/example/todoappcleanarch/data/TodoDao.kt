package com.example.todoappcleanarch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoappcleanarch.model.ToDoModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM to_do_table")
    fun getAllToDo() : Flow<List<ToDoModel>>

    @Query("SELECT * FROM to_do_table WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchToDo(searchQuery : String) : Flow<List<ToDoModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDo(toDoModel: ToDoModel)

    @Update
    suspend fun updateToDo(toDoModel: ToDoModel)

    @Query("SELECT * FROM to_do_table WHERE id = :toDoId")
    suspend fun getToDo(toDoId : Int) : ToDoModel

    @Delete
    suspend fun deleteToDo(toDoModel: ToDoModel)
}