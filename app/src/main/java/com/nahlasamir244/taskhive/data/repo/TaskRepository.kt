package com.nahlasamir244.taskhive.data.repo

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.nahlasamir244.taskhive.data.model.Task
import com.nahlasamir244.taskhive.data.offline.task.TaskDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(private val tasksDAO: TaskDAO) {

    suspend fun insert(task: Task){
        tasksDAO.insert(task)
    }

    suspend fun update(task: Task){
        tasksDAO.update(task)
    }

    suspend fun delete(task: Task){
        tasksDAO.delete(task)
    }

    fun getTasks(): Flow<List<Task>> = tasksDAO.getTasks()
}