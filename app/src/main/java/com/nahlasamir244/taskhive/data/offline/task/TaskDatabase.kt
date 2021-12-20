package com.nahlasamir244.taskhive.data.offline.task

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nahlasamir244.taskhive.data.model.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao() : TaskDAO
}