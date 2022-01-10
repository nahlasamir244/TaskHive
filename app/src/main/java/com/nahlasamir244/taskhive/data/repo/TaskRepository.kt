package com.nahlasamir244.taskhive.data.repo

import com.nahlasamir244.taskhive.data.model.Task
import com.nahlasamir244.taskhive.data.netwrok.TaskApiHelper
import com.nahlasamir244.taskhive.data.offline.task.TaskDAO
import com.nahlasamir244.taskhive.utils.SortType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(private val tasksDAO: TaskDAO,
                                         private val taskApiHelper: TaskApiHelper) {

    suspend fun insert(task: Task) {
        tasksDAO.insert(task)
    }

    suspend fun update(task: Task) {
        tasksDAO.update(task)
    }

    suspend fun delete(task: Task) {
        tasksDAO.delete(task)
    }

    fun getTasksSorted(searchKeyWord: String, sortType: SortType, hidCompleted: Boolean):
            Flow<List<Task>> =
        if (sortType == SortType.BY_NAME) tasksDAO.getTasksSortedByName(searchKeyWord, hidCompleted)
        else tasksDAO.getTasksSortedByDateCreated(searchKeyWord, hidCompleted)

    suspend fun deleteCompletedTasks(){
        tasksDAO.deleteCompletedTasks()
    }
    suspend fun getTasks() = taskApiHelper.getTasks()
}