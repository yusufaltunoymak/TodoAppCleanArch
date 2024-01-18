package com.example.todoappcleanarch.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class ToDoRepository @Inject constructor(localDataSource: LocalDataSource) {
    val localDataSource = localDataSource

}