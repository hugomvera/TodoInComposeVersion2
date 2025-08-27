package com.blacksnowymanx.todoincomposeversion2.roomListNames

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_names")
data class ListName(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String
)