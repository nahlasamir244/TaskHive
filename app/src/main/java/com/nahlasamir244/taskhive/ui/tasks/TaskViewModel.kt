package com.nahlasamir244.taskhive.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nahlasamir244.taskhive.data.offline.task.TaskDAO

class TaskViewModel @ViewModelInject constructor(
    private val taskDAO: TaskDAO
) : ViewModel() {
    //livedata can be observed as the single value not stream and it's lifecycle aware
    //flow has operators and can switch threads
    val taskList = taskDAO.getTasks().asLiveData()
}