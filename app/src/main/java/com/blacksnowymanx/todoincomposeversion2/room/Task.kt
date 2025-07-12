package com.blacksnowymanx.todoincomposeversion2.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Auto-incremented ID
    val title: String,                                 // Task title
    val description: String,                          // Task description
    val isCompleted: Boolean                          // Task completion status
)
