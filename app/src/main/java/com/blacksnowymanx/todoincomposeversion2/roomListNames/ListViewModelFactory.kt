package com.blacksnowymanx.todoincomposeversion2.roomListNames



import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ListNameViewModelFactory(private val listNameDao: ListNameDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListNameViewModel::class.java)) {
            return ListNameViewModel(listNameDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
