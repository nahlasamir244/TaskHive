package com.nahlasamir244.taskhive.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nahlasamir244.taskhive.data.offline.task.TaskDAO
import com.nahlasamir244.taskhive.data.repo.TaskRepository

class TaskViewModel @ViewModelInject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    //livedata can be observed as the single value not stream and it's lifecycle aware
    //flow has operators and can switch threads
    val taskList = taskRepository.getTasks().asLiveData()
}