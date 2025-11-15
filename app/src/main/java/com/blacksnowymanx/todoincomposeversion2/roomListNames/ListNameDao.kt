package com.blacksnowymanx.todoincomposeversion2.roomListNames

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ListNameDao {

    @Insert()
    suspend fun insert(listName: ListName)

    @Query("SELECT * FROM list_names ORDER BY id ASC")
     fun getAll(): LiveData<List<ListName>>

    @Delete
    suspend fun delete(listName: ListName)

    @Update
    suspend fun update(listName: ListName)


    @Query("SELECT name FROM list_names ORDER BY id ASC")
    fun getAllListNames(): LiveData<List<String>>


    @Query("SELECT name FROM list_names WHERE id = :id LIMIT 1")
    fun getListNameById(id: Int): LiveData<String>
}
