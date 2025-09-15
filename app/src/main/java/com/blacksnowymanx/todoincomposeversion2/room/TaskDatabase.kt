package com.blacksnowymanx.todoincomposeversion2.room
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.blacksnowymanx.todoincomposeversion2.roomListNames.ListName
import com.blacksnowymanx.todoincomposeversion2.roomListNames.ListNameDao
@Database(entities = [Task::class, ListName::class], version = 3, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun listNameDao(): ListNameDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                )
                    .fallbackToDestructiveMigration() // <- Add this line
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}