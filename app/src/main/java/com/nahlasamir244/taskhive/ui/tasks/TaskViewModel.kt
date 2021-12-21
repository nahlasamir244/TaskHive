package com.nahlasamir244.taskhive.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nahlasamir244.taskhive.data.repo.TaskRepository
import com.nahlasamir244.taskhive.utils.SortType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class TaskViewModel @ViewModelInject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    val tasksSortType = MutableStateFlow(SortType.BY_DATE)
    val hideCompletedTasks = MutableStateFlow(false)

    //stateflow is similar to livedata it can hold a single value not stream but it can be still used as flow
    var searchKeyWord = MutableStateFlow("")

    //    private val tasksFlow = searchKeyWord.flatMapLatest {
//        taskRepository.getTasks(it)
//    }

    //combine : takes multiple flows to single flow
    // and execute lambda whenever anyone of the flow emits new value
    private val tasksFlow = combine(
        searchKeyWord,
        tasksSortType,
        hideCompletedTasks
    ) { keyword, sortType, hideCompleted ->
        Triple(keyword, sortType, hideCompleted)
    }.flatMapLatest { (keyword, sortType, hideCompleted) ->
        taskRepository.getTasksSorted(keyword, sortType, hideCompleted)

    }

    //livedata can be observed as the single value not stream and it's lifecycle aware
    //flow has operators and can switch threads
    val taskList = tasksFlow.asLiveData()

}