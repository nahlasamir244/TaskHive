package com.nahlasamir244.taskhive.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.nahlasamir244.taskhive.data.offline.task.TaskDAO

class TaskViewModel @ViewModelInject constructor(
    private val taskDAO: TaskDAO
) : ViewModel() {

}