package com.nahlasamir244.taskhive.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nahlasamir244.taskhive.data.repo.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class TaskViewModel @ViewModelInject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    //stateflow is similar to livedata it can hold a single value not stream but it can be still used as flow
    var searchKeyWord = MutableStateFlow("")
    
    private val tasksFlow = searchKeyWord.flatMapLatest {
        taskRepository.getTasks(it)
    }
    //livedata can be observed as the single value not stream and it's lifecycle aware
    //flow has operators and can switch threads
    val taskList = tasksFlow.asLiveData()

}