package com.example.todoincomposeversion2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoincomposeversion2.room.Task
import com.example.todoincomposeversion2.room.TaskDao
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()
    fun insert(task: Task) {
        viewModelScope.launch {
            taskDao.insert(task)
        }
    }
    fun update(task: Task) {
        viewModelScope.launch {
            taskDao.update(task)
        }
    }
    fun delete(task: Task) {
        viewModelScope.launch {
            taskDao.delete(task)
        }
    }
}