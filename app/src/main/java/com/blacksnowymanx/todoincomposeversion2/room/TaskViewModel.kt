package com.blacksnowymanx.todoincomposeversion2.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    // Make sure this function exists and is spelled correctly
    fun getTasksByListName(listName: String): LiveData<List<Task>> { // Or Flow<List<Task>>
        return taskDao.getTasksByListName(listName)
    }





}