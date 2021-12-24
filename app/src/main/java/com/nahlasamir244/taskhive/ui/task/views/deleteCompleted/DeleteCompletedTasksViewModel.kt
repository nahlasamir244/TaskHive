package com.nahlasamir244.taskhive.ui.task.views.deleteCompleted

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.nahlasamir244.taskhive.data.repo.TaskRepository
import com.nahlasamir244.taskhive.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteCompletedTasksViewModel @ViewModelInject
constructor(
    private val taskRepository: TaskRepository,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {
    //we need the application scope to perform the deletion operation cause the dialog after clicking
    //any button is dismissed and its scope is cancelled
    fun onConfirmDeletionClicked() = applicationScope.launch {
        taskRepository.deleteCompletedTasks()
    }

}