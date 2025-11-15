package com.blacksnowymanx.todoincomposeversion2.roomListNames


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ListNameViewModel(private val listNameDao: ListNameDao) : ViewModel() {

    // Expose all list names as LiveData
    val allListNames: LiveData<List<ListName>> = listNameDao.getAll()

    // Insert a new ListName into the DB
    fun insert(listName: ListName) {
        viewModelScope.launch {
            listNameDao.insert(listName)
        }
    }

    // Function to update a list name
    fun update(listName: ListName) {
        viewModelScope.launch {
            listNameDao.update(listName)
        }
    }

    fun getById(id: Int): LiveData<String> {
        return  listNameDao.getListNameById(id)
    }


    // Delete a ListName
    fun delete(listName: ListName) {
        viewModelScope.launch {
            listNameDao.delete(listName)
        }
    }
}
