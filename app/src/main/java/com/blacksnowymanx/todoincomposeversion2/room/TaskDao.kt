package com.blacksnowymanx.todoincomposeversion2.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: Task) // Insert a new task.
    @Update
    suspend fun update(task: Task) // Update an existing task.
    @Delete
    suspend fun delete(task: Task) // Delete a task.
    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAllTasks(): LiveData<List<Task>> // Fetch all tasks.
}