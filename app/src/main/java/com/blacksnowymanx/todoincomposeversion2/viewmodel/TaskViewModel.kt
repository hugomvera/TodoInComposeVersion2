package com.blacksnowymanx.todoincomposeversion2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blacksnowymanx.todoincomposeversion2.room.Task
import com.blacksnowymanx.todoincomposeversion2.room.TaskDao
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {

    // this is where all the task will be put
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    //val listNames:LiveData<String> = taskDao.getAllListNames(vvvv)vvvvvvvvvvvvvvvvvvvvvvvv

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