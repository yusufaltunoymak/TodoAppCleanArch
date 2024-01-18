package com.example.todoappcleanarch.di

import android.content.Context
import androidx.room.Room
import com.example.todoappcleanarch.data.AppDatabase
import com.example.todoappcleanarch.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context : Context) = Room.databaseBuilder(
        context = context,
        klass = AppDatabase::class.java,
        name = Constants.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideToDoDao(database : AppDatabase) = database.toDoDao()


}